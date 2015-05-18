package com.appdynamics.loadgen;


import com.appdynamics.Util;
import com.appdynamics.crm.CRMRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by abey.tom on 10/15/14.
 */
public class CRMOnlineLoadGen {
    public static final Logger logger = LoggerFactory.getLogger(CRMOnlineLoadGen.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final TimeTracker tracker = new TimeTracker();

    public static void main(String[] args) {
        File dir = Util.getLocation();
        File propFile = new File(dir, "crmonline.properties");
        if (propFile.exists()) {
            Properties properties = loadProperties(propFile);
            EmailGenerator emailGenerator = new EmailGenerator(properties);
            startLoad(properties, propFile, emailGenerator);
        } else {
            logger.error("The property file 'crmonline.properties' is not present at {}" + propFile.getAbsolutePath());
        }
    }

    private static void startLoad(Properties properties, File propFile, EmailGenerator emailGenerator) {
        String baseUrl = properties.getProperty("load.http.base.url");
        Assert.hasText(baseUrl, propNotFoundMsg("load.http.base.url", propFile));
        baseUrl = baseUrl.trim();
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }

        try {
            ClientConfig config = new DefaultClientConfig();
            config.getClasses().add(JacksonJsonProvider.class);
            Client client = Client.create(config);
            client.resource(baseUrl).path("firstRequest").accept(MediaType.APPLICATION_JSON_TYPE).get(CRMRequest.class);
        } catch (Exception e) {
            logger.error("The server may not be started yet. Please start the server b4 load",e);
        }
        String delayStr = properties.getProperty("load.delay.millis");
        Assert.hasText(delayStr, propNotFoundMsg("load.delay.millis", propFile));
        long delay = Long.parseLong(delayStr);

        String customersStr = properties.getProperty("load.customers");
        Assert.hasText(customersStr, propNotFoundMsg("load.customers", propFile));
        String[] customers = customersStr.split(",");

        String urlsStr = properties.getProperty("load.urls");
        Assert.hasText(urlsStr, propNotFoundMsg("load.urls", propFile));
        String[] urls = urlsStr.split(",");
        List<Double> urlPercents = getUrlPercents(properties, urls);

        String regionsStr = properties.getProperty("load.regions");
        Assert.hasText(regionsStr, propNotFoundMsg("load.regions", propFile));
        String[] regions = regionsStr.split(",");

//        String customerPercentsStr = properties.getProperty("load.customer.percentages");
//        Assert.hasText(customerPercentsStr, propNotFoundMsg("load.customer.percentages", propFile));
//        List<Double> customerPercents = getCustomerPercents(customerPercentsStr.split(","));

        Map<String, List<Double>> urlRegionPercents = getUrlRegionPercents(properties, urls, regions);
        Random random = new Random();
        while (true) {
            try {
                int urlRandom = random.nextInt(100);
                String url = null;
                for (int i = 0; i < urlPercents.size(); i++) {
                    Double val = urlPercents.get(i);
                    if (urlRandom < val) {
                        url = urls[i];
                        break;
                    }
                }
                int locationRandom = random.nextInt(100);
                String location = null;
                List<Double> locationPercents = urlRegionPercents.get(url);
                for (int i = 0; i < locationPercents.size(); i++) {
                    Double val = locationPercents.get(i);
                    if (locationRandom < val) {
                        location = regions[i];
                        break;
                    }
                }
                logger.info(String.format("The url=%s and region=%s", url, location));
                int customerRandom = random.nextInt(100);
                String customer = customers[random.nextInt(customers.length)];
//                for (int i = 0; i < customerPercents.size(); i++) {
//                    Double val = customerPercents.get(i);
//                    if (customerRandom < val) {
//                        customer = customers[i];
//                        break;
//                    }
//                }

                makeHttpCall(customer, baseUrl, url, location, emailGenerator.generateEmail(customer));

                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static String propNotFoundMsg(String propName, File propFile) {
        String path;
        if (propFile != null) {
            path = propFile.getAbsolutePath();
        } else {
            path = "";
        }
        return String.format("The expected property '%s' is not found in the property file %s", propName, path);
    }

    private static void makeHttpCall(String customer, String baseUrl, String path, String region, String email) {
        CRMRequest request = new CRMRequest();
        request.setCustomerName(customer);
        request.setUserName(email);
        request.setUserRegion(region);
        String requestId = UUID.randomUUID().toString();
        String urlStr = baseUrl + encode(path) + "?requestId=" + requestId;
        logger.info("Invoking the CRM Online url " + urlStr);
        tracker.start();
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(OBJECT_MAPPER.writeValueAsBytes(request));
            os.flush();
            os.close();
            conn.getInputStream().close();
        } catch (MalformedURLException e) {
            logger.error("The url " + urlStr + " appears to be invalid");
        } catch (IOException e) {
            logger.error("Exception while invoking the url " + urlStr, e);
        } finally {
            long timeTaken = tracker.end();
            logger.info("Time taken for the request {} is {}", requestId, timeTaken);
            if (timeTaken > 300) {
                logger.info(">>>>>>> Time taken is {}  for {}", timeTaken, requestId);
            }

        }
    }

    private static String encode(String path) {
        try {
            return URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return path;
        }
    }

    private static Map<String, List<Double>> getUrlRegionPercents(Properties properties, String[] urls, String[] locations) {
        Map<String, List<Double>> urlLocationMap = new HashMap<String, List<Double>>();
        for (String url : urls) {
            double currentSum = 0;
            List<Double> urlLocationPercents = new ArrayList<Double>();
            for (String location : locations) {
                String propName = "load." + url + "." + location + ".percentage";
                String propValueStr = properties.getProperty(propName);
                Assert.hasText(propValueStr, propNotFoundMsg(propName, null));
                double val = Double.parseDouble(propValueStr);
                currentSum += val;
                urlLocationPercents.add(currentSum);
            }
            urlLocationMap.put(url, urlLocationPercents);
        }
        return urlLocationMap;
    }

    private static List<Double> getUrlPercents(Properties properties, String[] urls) {
        List<Double> urlPercents = new ArrayList<Double>();
        double currentSum = 0;
        for (String url : urls) {
            String propName = "load." + url + ".percentage";
            String propValStr = properties.getProperty(propName);
            Assert.hasText(propValStr, propNotFoundMsg(propName, null));
            double val = Double.parseDouble(propValStr);
            currentSum += val;
            urlPercents.add(currentSum);
        }
        return urlPercents;
    }

    private static List<Double> getCustomerPercents(String[] percents) {
        List<Double> customerPercents = new ArrayList<Double>();
        double currentSum = 0;
        for (int i = 0; i < percents.length; i++) {
            currentSum += Double.parseDouble(percents[i]);
            customerPercents.add(currentSum);
        }
        return customerPercents;
    }

    private static Properties loadProperties(File propFile) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propFile));
        } catch (IOException e) {
            logger.error("Error while reading the property file", e);
        }
        return props;
    }

    private static class Assert {

        public static void hasText(String str, String msg) {
            if (str == null || str.trim().isEmpty()) {
                throw new IllegalArgumentException(msg);
            }

        }
    }
}

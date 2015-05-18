package com.appdynamics.spring;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.HttpHeaders;
import java.util.Arrays;
import java.util.List;

/**
 * Created by abey.tom on 10/31/14.
 */
public class DeleteApplication {
    public static void main(String[] args) {
        String controllerHost = System.getProperty("appdynamics.controller.hostName");
        String controllerPort = System.getProperty("appdynamics.controller.port");
        List<String> appsToBeDel = Arrays.asList(args[0].split(","));

        StringBuilder sb = new StringBuilder();
        sb.append("user1@customer1").append(":").append("welcome");

        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        Client client = Client.create(config);
        WebResource resource = client.resource("http://" + controllerHost + ":" + controllerPort + "/controller");
        Application[] apps = resource.path("rest/applications")
                .queryParam("output", "JSON")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode(sb.toString())))
                .get(Application[].class);

        for (Application app : apps) {
            if (appsToBeDel.contains(app.getName())) {
                System.out.println("Deleting the application " + app.getName() + "   " + app.getId());
                resource.path("/restui/allApplications/deleteApplication")
                        .header(HttpHeaders.AUTHORIZATION, "Basic dXNlcjElNDBjdXN0b21lcjE6d2VsY29tZQ==")
                        .header("Content-Type","application/json;charset=UTF-8")
                        .post(String.valueOf(app.getId()));
            } else {
                System.out.println("App doesnt match");
            }
        }
    }
}

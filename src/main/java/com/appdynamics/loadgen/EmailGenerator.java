package com.appdynamics.loadgen;

import java.util.Properties;
import java.util.Random;

public class EmailGenerator {
    private String[] domains;
    private final String[] names;
    private Random random;

    public EmailGenerator(Properties properties) {
        String property = properties.getProperty("random.domains");
        if (property != null) {
            domains = property.split(",");
        }
        names = properties.getProperty("random.names").split(",");
        random = new Random();
    }

    public String generateEmail() {
        return names[random.nextInt(names.length)] + "@" + domains[random.nextInt(domains.length)];
    }

    public String generateEmail(String companyName) {
        String domain = companyName.replace(" ", "").toLowerCase() + ".com";
        return names[random.nextInt(names.length)] + "@"+domain;
    }
}
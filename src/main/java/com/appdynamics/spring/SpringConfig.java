package com.appdynamics.spring;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by abey.tom on 10/15/14.
 */
@Configuration
@ComponentScan(basePackages = "com.appdynamics")
@ImportResource(value = {"classpath:/hibernate-context.xml", "classpath:/rabbit-context.xml"})
public class SpringConfig {

    public static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    @Bean
    public Client restClient() {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        return Client.create(config);
    }
}

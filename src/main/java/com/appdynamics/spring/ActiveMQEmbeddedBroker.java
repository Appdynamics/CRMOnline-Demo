package com.appdynamics.spring;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abey.tom on 10/27/14.
 */
@Component
@Profile("activemq-broker")
public class ActiveMQEmbeddedBroker {
    public static final Logger logger = LoggerFactory.getLogger(ActiveMQEmbeddedBroker.class);

    @Value("${activemq.port}")
    private int port;

    @PostConstruct
    public void init() throws Exception {
        logger.info("Starting the broker on localhost ");
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:"+port);
        broker.start();
    }
}

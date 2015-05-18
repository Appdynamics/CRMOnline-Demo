package com.appdynamics.jetty;

import com.appdynamics.Util;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Created by abey.tom on 10/15/14.
 */
public class Start {
    private static final Logger LOG = Log.getLogger(Start.class);

    public static void main(String[] args) throws Exception {
        File baseDir = Util.getLocation();
        System.setProperty("war.base.dir",baseDir.getAbsolutePath());
        Server server = new Server();
        SocketConnector connector = new SocketConnector();

        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        int jettyPort = getPort();
        connector.setPort(jettyPort);
        server.setConnectors(new Connector[] { connector });

        ProtectionDomain protectionDomain = Start.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();

        WebAppContext webapp = new WebAppContext();
        webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);

        try {
            server.start();
            LOG.info("Jetty Server Started at port {}",jettyPort);
            server.join();
        } catch (Exception e) {
            LOG.info("Error in jetty Start ",e);
            System.exit(100);
        }
    }

    private static int getPort() {
        String port = System.getProperty("jetty.port");
        if (port != null && !port.trim().isEmpty()) {
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                return 8080;
            }
        } else {
            return 8080;
        }
    }
}

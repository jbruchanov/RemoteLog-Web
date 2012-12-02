package com.scurab.gwt.rlw.server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Application implements ServletContextListener {

    public static final Properties APPLICATION_PROPERTIES = new Properties();

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Start");
        try {
            loadProperties();
            Database.init();
            if (Boolean.parseBoolean(APPLICATION_PROPERTIES.getProperty("createschema"))) {
                System.err
                        .println("RECREATINGSCHEMA - YOU MUST DISABLE createschema in appsettings.properties to proper start!");
                Database.createSchema();
                Connection con = Database.getConnection();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadProperties() throws IOException {
        String file = "/remotelogweb.properties";
        InputStream inputStream = Application.class.getResourceAsStream(file);
        APPLICATION_PROPERTIES.load(inputStream);
        inputStream.close();
    }

    public static final class ApplicationPropertyKeys {
        public static final String connection_url = "hibernate.connection.url";
        public static final String connection_username = "hibernate.connection.username";
        public static final String connection_password = "hibernate.connection.password";
        public static final String createschema = "createschema";
    }

}

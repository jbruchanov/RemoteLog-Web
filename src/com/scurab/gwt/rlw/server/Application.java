package com.scurab.gwt.rlw.server;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scurab.gwt.rlw.server.data.Database;

public class Application implements ServletContextListener {

    public static final Properties APPLICATION_PROPERTIES = new Properties();
    public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd kk:mm:ss.SSS").create();

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Start");
        try {
            loadProperties();
            Database.init();

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
    }

}

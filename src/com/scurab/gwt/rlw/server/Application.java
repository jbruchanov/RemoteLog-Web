package com.scurab.gwt.rlw.server;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.collections.map.HashedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gwt.dev.javac.Shared;
import com.scurab.gwt.rlw.server.data.Database;
import com.scurab.gwt.rlw.shared.SharedParams;

public class Application implements ServletContextListener {

    public static final Properties APP_PROPS = new Properties();
    public static final HashMap<String, Object> CLIENT_PROPERTIES = new HashMap<String,Object>();
    private static String DT_FORMAT = "yyyy-MM-dd kk:mm:ss.SSS";
    public static SimpleDateFormat DATEFORMAT = new SimpleDateFormat(DT_FORMAT);
    public static final Gson GSON = new GsonBuilder().setDateFormat(DT_FORMAT).create();
    public static int PAGE_SIZE = 50;

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
        APP_PROPS.load(inputStream);
        inputStream.close();
        if(APP_PROPS.containsKey(SharedParams.CLIENT_DEFAULT_DATE_FORMAT)){
            DT_FORMAT = APP_PROPS.getProperty(SharedParams.CLIENT_DEFAULT_DATE_FORMAT);
            DATEFORMAT = new SimpleDateFormat(DT_FORMAT);
        }
        CLIENT_PROPERTIES.put(SharedParams.CLIENT_DEFAULT_DATE_FORMAT, DT_FORMAT);
        if(APP_PROPS.containsKey(SharedParams.CLIENT_PAGE_SIZE)){
            try{
                PAGE_SIZE = Integer.parseInt(APP_PROPS.getProperty(SharedParams.CLIENT_PAGE_SIZE));
            }catch(Exception e){
                System.err.print("Invalid value for property PAGE_SIZE");
                e.printStackTrace();
            }
        }
        CLIENT_PROPERTIES.put(SharedParams.CLIENT_PAGE_SIZE, PAGE_SIZE);
    }

    public static final class ApplicationPropertyKeys {
        public static final String connection_url = "hibernate.connection.url";
        public static final String connection_username = "hibernate.connection.username";
        public static final String connection_password = "hibernate.connection.password";
    }

}

package com.scurab.gwt.rlw.server;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scurab.gwt.rlw.server.data.Database;
import com.scurab.gwt.rlw.server.data.web.LogItemsConnector;
import com.scurab.gwt.rlw.server.util.DoubleHashMap;
import com.scurab.gwt.rlw.server.util.XmlLoader;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.PushMessage;

public class Application implements ServletContextListener {

    public static final Properties APP_PROPS = new Properties();
    
    public static final HashMap<String, Object> CLIENT_PROPERTIES = new HashMap<String,Object>();
    
    /** Default format for datetime, must be same like on client! **/
    private static String DT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    public static SimpleDateFormat DATEFORMAT = new SimpleDateFormat(DT_FORMAT);
    
    private static final Gson GSON = new GsonBuilder().setDateFormat(DT_FORMAT).create();
    
    /** defualt page size **/
    public static int PAGE_SIZE = 50;

    public static PushMessage[] PUSH_MESSAGES;
    
    public static DoubleHashMap<String, String, String> SERVER_PUSH_KEYS;
    
    private static boolean sGsonVerbose = false;
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        onClean();
        System.out.println(getTime() + " contextDestroyed");
    }
    
    private void onClean(){
        if(APP_PROPS != null){
            APP_PROPS.clear();
        }
        
        if(CLIENT_PROPERTIES != null){
            CLIENT_PROPERTIES.clear();
        }
        
        PUSH_MESSAGES = null;
        
        if(SERVER_PUSH_KEYS != null){
            SERVER_PUSH_KEYS.clear();
            SERVER_PUSH_KEYS = null;
        }
        
        //release resources for db
        Database.dispose();
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println(getTime() + " contextInitialized");
        try {
            loadProperties();
            loadPushMessages();
            loadPushServerKeys();
            Database.init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPushServerKeys() {
        SERVER_PUSH_KEYS = XmlLoader.loadServerPushTokens();
    }

    /**
     * Load app properties and fill ClientProperties collection
     * @throws IOException
     */
    public static void loadProperties() throws IOException {
        String file = "/remotelogweb.properties";
        InputStream inputStream = Application.class.getResourceAsStream(file);
        APP_PROPS.load(inputStream);
        inputStream.close();

        //date format
        if(APP_PROPS.containsKey(SharedParams.CLIENT_DEFAULT_DATE_FORMAT)){
            DT_FORMAT = APP_PROPS.getProperty(SharedParams.CLIENT_DEFAULT_DATE_FORMAT);
            DATEFORMAT = new SimpleDateFormat(DT_FORMAT);
        }
        CLIENT_PROPERTIES.put(SharedParams.CLIENT_DEFAULT_DATE_FORMAT, DT_FORMAT);

        //page size
        if(APP_PROPS.containsKey(SharedParams.CLIENT_PAGE_SIZE)){
            try{
                PAGE_SIZE = Integer.parseInt(APP_PROPS.getProperty(SharedParams.CLIENT_PAGE_SIZE));
            }catch(Exception e){
                System.err.print("Invalid value for property PAGE_SIZE");
                e.printStackTrace();
            }
        }
        CLIENT_PROPERTIES.put(SharedParams.CLIENT_PAGE_SIZE, PAGE_SIZE);

        //autorefresh
        if(APP_PROPS.containsKey(SharedParams.CLIENT_AUTO_REFRESH)){
            try{
                int refresh = Integer.parseInt(APP_PROPS.getProperty(SharedParams.CLIENT_AUTO_REFRESH));
                CLIENT_PROPERTIES.put(SharedParams.CLIENT_AUTO_REFRESH, refresh);
            }catch(Exception e){
                System.err.print("Invalid value for property AUTO_REFRESH");
                e.printStackTrace();
            }
        }
                
        if(APP_PROPS.containsKey(SharedParams.GSON_VERBOSE)){
            sGsonVerbose = Boolean.parseBoolean(APP_PROPS.getProperty(SharedParams.GSON_VERBOSE));
        }
        
        if(APP_PROPS.containsKey(SharedParams.SERVER_TIME_FOR_LOG_ITEMS)){
            LogItemsConnector.SERVER_TIME_FOR_LOG_ITEMS = Boolean.parseBoolean(APP_PROPS.getProperty(SharedParams.SERVER_TIME_FOR_LOG_ITEMS));
        }
    }
    
    public static void loadPushMessages(){
        PUSH_MESSAGES = XmlLoader.loadMessages();
    }

    public static final class ApplicationPropertyKeys {
        public static final String connection_url = "hibernate.connection.url";
        public static final String connection_username = "hibernate.connection.username";
        public static final String connection_password = "hibernate.connection.password";
    }

    public static String toJson(Object o){
        if(sGsonVerbose){
            System.out.println(String.format("%s 2Json [%s]:%s", getTime(), o.getClass().getName(), o));
        }
        return GSON.toJson(o);
    }
    
    public static <T> T fromJson(String json, Class <? extends T> type){
        if(sGsonVerbose){
            System.out.println("------------FROM JSON----------------");
            System.out.println(getTime());
            System.out.println(json);
        }
        T t = GSON.fromJson(json, type);
        if(sGsonVerbose){
            System.out.println("/------------FROM JSON----------------");
        }
        return t;
    }
    
    private static String getTime(){
        return "[" + DATEFORMAT.format(System.currentTimeMillis()) + "]";
    }
}

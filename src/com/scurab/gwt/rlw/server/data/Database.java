package com.scurab.gwt.rlw.server.data;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Queries;
import com.scurab.gwt.rlw.server.annotation.DefaultOrderString;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.LogItemBlob;
import com.scurab.gwt.rlw.shared.model.Settings;

@SuppressWarnings("serial")
public class Database {
    private static SessionFactory factory;

    public static Session openSession() {
        return factory.openSession();
    }

    /**
     * Init DB connection
     */
    public static void init() {
        if (factory == null) {
            Queries.init();
            Configuration config = getConfiguration();
            factory = config.buildSessionFactory();
            checkTables();
        }
    }
    
    public static void dispose(){
        if(factory != null){
            try{
                factory.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            factory = null;
        }
    }

    /**
     * Check tables, if there is 0 tables, create them
     */
    private static void checkTables() {
        Session s = openSession();
        int size = s.createSQLQuery("SHOW TABLES;").list().size();
        s.close();
        if (size == 0) {
            createSchema();
        }
    }

    public static Connection getConnection() throws SQLException {
        return getConnection(Application.APP_PROPS
                .getProperty(Application.ApplicationPropertyKeys.connection_url));
    }

    public static Connection getConnection(String url) throws SQLException {
        return DriverManager
                .getConnection(url, Application.APP_PROPS
                        .getProperty(Application.ApplicationPropertyKeys.connection_username),
                        Application.APP_PROPS
                        .getProperty(Application.ApplicationPropertyKeys.connection_password));
    }

    @SuppressWarnings("rawtypes")
    public static List getDataByQuery(Session s, String query, boolean isSQL) throws Exception {
        s.beginTransaction();
        Query q = null;
        if (isSQL) {
            q = s.createSQLQuery(query);
        } else {
            q = s.createQuery(query);
        }
        List result = q.list();
        return result;
    }

    public static void createSchema() {
        Configuration config = getConfiguration();
        factory.close();
        factory = config.buildSessionFactory();
        SchemaExport se = new SchemaExport(getConfiguration());
        se.create(true, true);
    }

    private static Configuration getConfiguration() {

        Configuration c = new Configuration();

        c.addAnnotatedClass(Device.class);
        c.addAnnotatedClass(LogItem.class);
        c.addAnnotatedClass(LogItemBlob.class);
        c.addAnnotatedClass(Settings.class);

        c.setProperties(Application.APP_PROPS);
        c.configure();

        return c;
    }

    public static class TableInfo {
        public String TableName;
        public String DefaultOrderString;
    }

    /**
     * Get table info based on class annotations
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> TableInfo getTable(Class<T> object) {
        Class<T> c;
        TableInfo result = null;
        try {
            java.lang.annotation.Annotation[] as = object.getDeclaredAnnotations();
            Entity entity = null;
            DefaultOrderString orderString = null;
            for (Annotation a : as) {
                if (a.annotationType().equals(Entity.class)) {
                    entity = (Entity) a;
                    continue;
                }
                if (a.annotationType().equals(DefaultOrderString.class)) {
                    orderString = (DefaultOrderString) a;
                    continue;
                }
            }

            result = new TableInfo();
            if (entity != null) {
                result.TableName = entity.name();
            }

            if (result.TableName == null || result.TableName.length() == 0) {
                result.TableName = object.getSimpleName();
            }

            if (orderString != null) {
                result.DefaultOrderString = orderString.value();
            }
        } catch (Exception e1) {
            // should not happen
            e1.printStackTrace();
        }
        return result;
    }
}

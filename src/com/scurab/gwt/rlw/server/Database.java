package com.scurab.gwt.rlw.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

@SuppressWarnings("serial")
public class Database {
    private static SessionFactory factory;

    public static Session openSession() {
        return factory.openSession();
    }

    public static void init() {
        if (factory == null) {
            Queries.init();
            Configuration config = getConfiguration();
            factory = config.buildSessionFactory();
            checkTables();
        }
    }

    private static void checkTables() {
        Session s = openSession();
        int size = s.createSQLQuery("SHOW TABLES;").list().size();
        s.close();
        if (size == 0) {
            createSchema();
        }
    }

    public static Connection getConnection() throws SQLException {
        return getConnection(Application.APPLICATION_PROPERTIES
                .getProperty(Application.ApplicationPropertyKeys.connection_url));
    }

    public static Connection getConnection(String url) throws SQLException {
        return (Connection) DriverManager
                .getConnection(url, Application.APPLICATION_PROPERTIES
                        .getProperty(Application.ApplicationPropertyKeys.connection_username),
                        Application.APPLICATION_PROPERTIES
                                .getProperty(Application.ApplicationPropertyKeys.connection_password));
    }

    @SuppressWarnings("rawtypes")
    public static List getDataByQuery(Session s, String query, boolean isSQL) throws Exception {
        s.beginTransaction();
        Query q = null;
        if (isSQL)
            q = s.createSQLQuery(query);
        else
            q = s.createQuery(query);
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

        c.setProperties(Application.APPLICATION_PROPERTIES);
        c.configure();

        return c;
    }

    public static class TableInfo {
        public String TableName;
        public String DefaultOrderString;
    }
}

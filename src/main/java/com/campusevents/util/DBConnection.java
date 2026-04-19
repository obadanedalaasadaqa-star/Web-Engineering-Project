package com.campusevents.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            InputStream is = DBConnection.class.getClassLoader()
                .getResourceAsStream("db.properties");
            if (is == null) {
                throw new RuntimeException("db.properties not found on classpath");
            }
            Properties props = new Properties();
            props.load(is);
            url      = props.getProperty("db.url");
            user     = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

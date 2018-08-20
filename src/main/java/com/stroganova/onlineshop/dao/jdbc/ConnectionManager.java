package com.stroganova.onlineshop.dao.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private String url;
    private String user;
    private String password;

    public ConnectionManager(Properties properties) {
        try {
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("error while loading driver", e);
        }
        this.url = properties.getProperty("jdbc.url");
        this.user = properties.getProperty("jdbc.username");
        this.password = properties.getProperty("jdbc.password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("error while getting connection", e);
        }
    }
}

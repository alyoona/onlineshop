package com.stroganova.onlineshop.dao.jdbc;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.*;


public class ConnectionManagerTest {
    private Properties properties;
    private ConnectionManager connectionManager;

    @Before
    public void before() {
        properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("src/main/resources/OnlineShop.properties"))
        ) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("error while loading properties file", e);
        }
        connectionManager = new ConnectionManager(properties);
    }

    @Test
    public void getConnectionTest() {
        try (Connection connection = connectionManager.getConnection()) {
            assertNotNull(connection);
        } catch (SQLException e) {
            throw new RuntimeException("error while getting connection", e);
        }
    }
}
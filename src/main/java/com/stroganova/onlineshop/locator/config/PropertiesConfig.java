package com.stroganova.onlineshop.locator.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {

    public static Properties get() {
        //Properties
        try (InputStream inputStream = PropertiesConfig.class.getResourceAsStream("/application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("error while loading properties file", e);
        }

    }

}

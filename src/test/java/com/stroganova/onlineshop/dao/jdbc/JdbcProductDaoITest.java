package com.stroganova.onlineshop.dao.jdbc;

import com.stroganova.onlineshop.entity.Product;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class JdbcProductDaoITest {
    private Properties properties;
    private ConnectionManager connectionManager;
    private JdbcProductDao jdbcProductDao;

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
        jdbcProductDao = new JdbcProductDao();
        jdbcProductDao.setConnectionManager(connectionManager);
    }

    @Test
    public void getAll() {
        List<Product> allProducts = jdbcProductDao.getAll();
        if (!allProducts.isEmpty()) {
            for (Product product : allProducts) {
                assertNotNull(product.getId());
                assertNotNull(product.getName());
                assertNotNull(product.getPrice());
            }
        }
    }

    @Test
    public void addTest() {
        //prepare
        Product product = new Product();
        product.setName("testProduct");
        product.setDescription("testDescription");
        product.setPrice(156.98);
        product.setPicturePath("testPicturePath");
        //when
        jdbcProductDao.add(product);
        List<Product> allProducts = jdbcProductDao.getAll();
        //then
        assertTrue(allProducts.contains(product));
    }
}
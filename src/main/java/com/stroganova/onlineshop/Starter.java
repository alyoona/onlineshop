package com.stroganova.onlineshop;

import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.dao.jdbc.ConnectionManager;
import com.stroganova.onlineshop.dao.jdbc.JdbcProductDao;
import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.web.servlet.AddProductServlet;
import com.stroganova.onlineshop.web.servlet.AssetsServlet;
import com.stroganova.onlineshop.web.servlet.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Starter  {

    public static void main(String[] args) throws Exception {
        //Properties
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("src/main/resources/OnlineShop.properties"))
        ) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("error while loading properties file", e);
        }

        //DB
        ConnectionManager connectionManager = new ConnectionManager(properties);

        //dao
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        jdbcProductDao.setConnectionManager(connectionManager);

        //services
        ProductService productService = new ProductService();
        productService.setProductDao(jdbcProductDao);

        //servlets
        ProductsServlet productsServlet = new ProductsServlet();
        productsServlet.setProductService(productService);

        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);

        //server config
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(productsServlet), "/products/*");
        servletContextHandler.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");
        servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/products/add");

        //start
        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
    }
}

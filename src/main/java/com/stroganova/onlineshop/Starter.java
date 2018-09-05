package com.stroganova.onlineshop;

import com.stroganova.onlineshop.dao.jdbc.JdbcProductDao;
import com.stroganova.onlineshop.dao.jdbc.JdbcUserDao;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.UserService;
import com.stroganova.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Starter  {

    public static void main(String[] args) throws Exception {
        //Properties
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("src/main/resources/application.properties"))
        ) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("error while loading properties file", e);
        }

        //DB
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(properties.getProperty("jdbc.url"));
        dataSource.setUser(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));

        //dao
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSource);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

        //services
        ProductService productService = new ProductService();
        productService.setProductDao(jdbcProductDao);

        UserService userService = new UserService();
        userService.setUserDao(jdbcUserDao);

        //servlets
        ProductsServlet productsServlet = new ProductsServlet();
        productsServlet.setProductService(productService);
        productsServlet.setUserService(userService);

        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);
        addProductServlet.setUserService(userService);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setUserService(userService);

        LogoutServlet logoutServlet = new LogoutServlet();
        logoutServlet.setUserService(userService);

        RegisterServlet registerServlet = new RegisterServlet();
        registerServlet.setUserService(userService);

        //server config
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(productsServlet), "/products/*");
        servletContextHandler.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");
        servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        servletContextHandler.addServlet(new ServletHolder(logoutServlet), "/logout");
        servletContextHandler.addServlet(new ServletHolder(registerServlet), "/register");

        //start
        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
    }
}

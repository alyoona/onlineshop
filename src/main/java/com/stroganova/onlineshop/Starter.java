package com.stroganova.onlineshop;

import com.stroganova.onlineshop.dao.jdbc.JdbcProductDao;
import com.stroganova.onlineshop.dao.jdbc.JdbcUserDao;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.service.UserService;
import com.stroganova.onlineshop.web.filter.SecurityFilter;
import com.stroganova.onlineshop.web.filter.UserRoleFilter;
import com.stroganova.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;

public class Starter {

    public static void main(String[] args) throws Exception {
        //Properties
        Properties properties = new Properties();
        try (InputStream inputStream = Starter.class.getResourceAsStream("/application.properties")) {
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

        SecurityService securityService = new SecurityService();
        securityService.setUserService(userService);


        //filters
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setSecurityService(securityService);

        UserRoleFilter userRoleFilter = new UserRoleFilter();


        //servlets
        ProductsServlet productsServlet = new ProductsServlet();
        productsServlet.setProductService(productService);
        productsServlet.setSecurityService(securityService);

        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);
        addProductServlet.setSecurityService(securityService);


        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setSecurityService(securityService);

        LogoutServlet logoutServlet = new LogoutServlet();
        logoutServlet.setSecurityService(securityService);

        RegisterServlet registerServlet = new RegisterServlet();
        registerServlet.setSecurityService(securityService);

        CartServlet cartServlet = new CartServlet();
        cartServlet.setProductService(productService);

        //server config
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        ServletHolder productsServletHolder = new ServletHolder(productsServlet);
        servletContextHandler.addServlet(productsServletHolder, "/");
        servletContextHandler.addServlet(productsServletHolder, "/products");
        servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/products/add");

        servletContextHandler.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");

        servletContextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        servletContextHandler.addServlet(new ServletHolder(logoutServlet), "/logout");
        servletContextHandler.addServlet(new ServletHolder(registerServlet), "/register");
        servletContextHandler.addServlet(new ServletHolder(cartServlet), "/cart");


        //filter config
        FilterHolder filterHolderForSecurityFilter = new FilterHolder(securityFilter);
        FilterHolder filterHolderForUserRoleFilter = new FilterHolder(userRoleFilter);

        servletContextHandler.addFilter(filterHolderForSecurityFilter, "/products/add",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        servletContextHandler.addFilter(filterHolderForUserRoleFilter, "/products/add",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

        servletContextHandler.addFilter(filterHolderForSecurityFilter, "/products",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        servletContextHandler.addFilter(filterHolderForSecurityFilter, "/cart",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        servletContextHandler.addFilter(filterHolderForSecurityFilter, "/",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

        //start
        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
    }
}

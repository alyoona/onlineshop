package com.stroganova.onlineshop;

import com.stroganova.onlineshop.dao.jdbc.JdbcProductDao;
import com.stroganova.onlineshop.dao.jdbc.JdbcUserDao;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.service.UserService;
import com.stroganova.onlineshop.web.filter.AdminRoleFilter;
import com.stroganova.onlineshop.web.filter.SecurityFilter;
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
        securityService.setSessionDuration(Long.parseLong(properties.getProperty("sessionDurationSeconds")));


        //servlets
        ProductsServlet productsServlet = new ProductsServlet();
        productsServlet.setProductService(productService);

        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);

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
        servletContextHandler.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");
        ServletHolder productsServletHolder = new ServletHolder(productsServlet);
        servletContextHandler.addServlet(productsServletHolder, "/");
        servletContextHandler.addServlet(productsServletHolder, "/products");
        servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(cartServlet), "/cart");
        servletContextHandler.addServlet(new ServletHolder(logoutServlet), "/logout");
        servletContextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        servletContextHandler.addServlet(new ServletHolder(registerServlet), "/register");

        //filters
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setSecurityService(securityService);

        //filter config
        FilterHolder userRoleFilterHolder = new FilterHolder(securityFilter);
        FilterHolder adminRoleFilterHolder = new FilterHolder(new AdminRoleFilter());

        //SecurityFilter mapping
        servletContextHandler.addFilter(userRoleFilterHolder, "/",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        servletContextHandler.addFilter(userRoleFilterHolder, "/products/*",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        servletContextHandler.addFilter(userRoleFilterHolder, "/cart",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        servletContextHandler.addFilter(userRoleFilterHolder, "/logout",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

        //AdminRoleFilter mapping
        servletContextHandler.addFilter(adminRoleFilterHolder, "/products/add",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

        //start
        int port = Integer.parseInt(System.getProperty("port", "8080"));
        Server server = new Server(port);
        server.setHandler(servletContextHandler);

        server.start();
    }
}

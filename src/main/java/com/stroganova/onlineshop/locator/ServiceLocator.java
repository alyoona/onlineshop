package com.stroganova.onlineshop.locator;

import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.dao.jdbc.JdbcProductDao;
import com.stroganova.onlineshop.dao.jdbc.JdbcUserDao;
import com.stroganova.onlineshop.locator.config.DataSourceConfig;
import com.stroganova.onlineshop.locator.config.PropertiesConfig;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.service.UserService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Map<Class, Object> CACHE = new HashMap<>();

    public static <T> T getService(Class<T> clazz) {
        T service = clazz.cast(CACHE.get(clazz));
        if (service != null) {
            return service;
        }
        try {
            service = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating new instance", e);
        }
        CACHE.put(clazz, service);
        return service;
    }

    static {
        //Properties
        Properties properties = PropertiesConfig.get();
        //DB
        DataSource dataSource = DataSourceConfig.setUp(properties);
        //dao config
        JdbcProductDao productDao = getService(JdbcProductDao.class);
        productDao.setDataSource(dataSource);
        JdbcUserDao userDao = getService(JdbcUserDao.class);
        userDao.setDataSource(dataSource);
        //services config
        ProductService productService = getService(ProductService.class);
        productService.setProductDao(productDao);
        UserService userService = getService(UserService.class);
        userService.setUserDao(userDao);
        SecurityService securityService = getService(SecurityService.class);
        securityService.setUserService(userService);
        securityService.setSessionDuration(Long.parseLong(properties.getProperty("sessionDurationSeconds")));
    }
}


package com.stroganova.onlineshop.service.impl;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceDefault implements UserService {

    private UserDao userDao;

    public UserServiceDefault(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }
}


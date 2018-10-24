package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.entity.User;

import java.util.Optional;

public class UserService {

    private UserDao userDao;

    public User getUser(String username) {
        return userDao.getUser(username);
    }

    public void add(User user) {
        userDao.add(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}



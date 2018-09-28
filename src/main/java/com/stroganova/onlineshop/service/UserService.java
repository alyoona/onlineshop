package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.entity.User;

public class UserService {

    private UserDao userDao;

    public User getUser(String username, String password) {
        return userDao.getUser(username, password);
    }

    public void add(User user) {
        userDao.add(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}

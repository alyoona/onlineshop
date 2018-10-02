package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.entity.User;

public class UserService {

    private UserDao userDao;

    User getUser(String username) {
        return userDao.getUser(username);
    }

    public void add(User user) {
        userDao.add(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}

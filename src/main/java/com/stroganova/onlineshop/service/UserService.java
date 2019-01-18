package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.entity.User;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(String username) {
        return userDao.getUser(username);
    }

    public void add(User user) {
        userDao.add(user);
    }


}



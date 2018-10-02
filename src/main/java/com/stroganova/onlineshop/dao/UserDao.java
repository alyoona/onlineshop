package com.stroganova.onlineshop.dao;

import com.stroganova.onlineshop.entity.User;

public interface UserDao {

    User getUser(String username);

    void add(User user);

}

package com.stroganova.onlineshop.dao;

import com.stroganova.onlineshop.entity.User;

public interface UserDao {

    User getUser(String username, String password);

    void add(User user);


}

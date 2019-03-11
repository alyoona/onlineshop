package com.stroganova.onlineshop.service;


import com.stroganova.onlineshop.entity.User;

public interface UserService {

    User getUser(String username);

    void add(User user);
}

package com.stroganova.onlineshop.dao;

import com.stroganova.onlineshop.entity.User;

public interface UserDao {

    boolean isIdentified(User user);

    boolean isIdentified(String login);

    User getUser(String userToken);

    void setUserToken(User user);

    void add(User user);


}

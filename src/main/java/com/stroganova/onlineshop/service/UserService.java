package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.entity.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class UserService {

    private UserDao userDao;

    public boolean isIdentified(String login, String password) {

        return userDao.isIdentified(new User(login, password));
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(String userToken) {
        return userDao.getUser(userToken);
    }

    public void setUserToken(User user) {
        userDao.setUserToken(user);
    }

    public boolean isIdentified(String login) {
        return userDao.isIdentified(login);
    }

    public void add(User user) {
        userDao.add(user);
    }

    public String getAuthorizedUserLogin(HttpServletRequest request){
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("user-token")) {
                User user = this.getUser(cookie.getValue());
                if(user != null) {
                    return user.getLogin();
                }
            }
        }
        return null;
    }
}

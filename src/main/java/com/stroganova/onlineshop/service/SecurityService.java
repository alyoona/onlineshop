package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SecurityService {

    UserService userService;

    List<Session> sessionsList = new ArrayList<>();

    public Session auth(String username, String password) {
        User user = userService.getUser(username, password);
        if (user != null) {
            Session session = new Session();
            String uuid = UUID.randomUUID().toString();
            session.setToken(uuid);
            session.setUser(user);
            sessionsList.add(session);
            return session;
        }
        return null;
    }

    public void logout(String token) {
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while(sessionIterator.hasNext()) {
            Session session = sessionIterator.next();
            if (token.equals(session.getToken())) {
                sessionIterator.remove();
            }
        }
    }

    public Session getSession(String token) {
        for (Session session : sessionsList) {
            if (token.equals(session.getToken())) {
                return session;
            }
        }
        return null;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecurityService{");
        sb.append("sessionsList=").append(sessionsList);
        sb.append('}');
        return sb.toString();
    }
}

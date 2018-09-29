package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;

import java.time.LocalDateTime;
import java.util.*;

public class SecurityService {

    private UserService userService;

    private List<Session> sessionsList = Collections.synchronizedList(new ArrayList<>());

    public Session register(String username, String password) {
        User user = new User();
        user.setLogin(username);
        user.setPassword(password);
        userService.add(user);
        return auth(username, password);
    }

    public Session auth(String username, String password) {
        User user = userService.getUser(username, password);
        if (user != null) {
            Session session = new Session();
            String uuid = UUID.randomUUID().toString();
            session.setToken(uuid);
            session.setUser(user);
            session.setExpireDate(LocalDateTime.now().plusSeconds(15));
            sessionsList.add(session);
            return session;
        }
        return null;
    }

    public void logout(String token) {
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while (sessionIterator.hasNext()) {
            Session session = sessionIterator.next();
            if (token.equals(session.getToken())) {
                sessionIterator.remove();
            }
        }
    }

    public Session getSession(String token) {
        for (Session session : sessionsList) {
            if (token != null) {
                if (token.equals(session.getToken())) {
                    if (LocalDateTime.now().isAfter(session.getExpireDate())) {
                        logout(token);
                        return null;
                    }
                    return session;
                }
            }
        }
        return null;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String toString() {
        return "SecurityService{" +
                "sessionsList=" + sessionsList +
                '}';
    }
}

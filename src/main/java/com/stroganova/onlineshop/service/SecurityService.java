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
        Session session = auth(username, password);
        return session;
    }

    public Session auth(String username, String password) {
        User user = userService.getUser(username, password);
        if (user != null) {
            Session session = new Session();
            String uuid = UUID.randomUUID().toString();
            session.setToken(uuid);
            session.setUser(user);
            session.setExpireDate(LocalDateTime.now().plusHours(3));
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
            if (token != null) {
                if (token.equals(session.getToken())) {
//                if(session.getExpireDate().isAfter(LocalDateTime.now())) {
//                    System.out.println("Session is expired!");
//                    sessionsList.remove(session);
//                    return null;
//                }
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
        final StringBuilder sb = new StringBuilder("SecurityService{");
        sb.append("sessionsList=").append(sessionsList);
        sb.append('}');
        return sb.toString();
    }
}

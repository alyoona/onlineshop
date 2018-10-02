package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.*;

public class SecurityService {

    private UserService userService;

    private List<Session> sessionsList = Collections.synchronizedList(new ArrayList<>());

    public Session register(String username, String password) {
        String salt = UUID.randomUUID().toString();
        String hashedPassword = DigestUtils.md5Hex(password + salt);

        User user = new User();
        user.setLogin(username);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        userService.add(user);
        return getSessionForUser(user);
    }

    private Session getSessionForUser(User user) {
        if (user != null) {
            Session session = new Session();
            String uuid = UUID.randomUUID().toString();
            session.setToken(uuid);
            session.setUser(user);
            session.setExpireDate(LocalDateTime.now().plusHours(1));
            sessionsList.add(session);
            return session;
        }
        return null;
    }

    public Session login(String username, String password) {
        User user = auth(username, password);
        if (user != null) {
            return getSessionForUser(user);
        }
        return null;
    }

    private User auth(String username, String password) {
        User user = userService.getUser(username);
        String userHashedPassword = user.getPassword();
        String salt = user.getSalt();
        String incomingHashedPassword = DigestUtils.md5Hex(password + salt);
        if (userHashedPassword.equals(incomingHashedPassword)) {
            return user;
        } else return null;
    }

    public void logout(String token) {
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while (sessionIterator.hasNext()) {
            Session session = sessionIterator.next();
            if (token.equals(session.getToken())) {
                sessionIterator.remove();
                break;
            }
        }
    }

    public Session getSession(String token) {
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while (sessionIterator.hasNext()) {
            Session session = sessionIterator.next();
            if (token.equals(session.getToken())) {
                if (LocalDateTime.now().isAfter(session.getExpireDate())) {
                    sessionIterator.remove();
                    return null;
                }
                return session;
            }
        }
        return null;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}

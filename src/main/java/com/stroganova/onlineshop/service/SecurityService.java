package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

public class SecurityService {

    private UserService userService;
    private List<Session> sessionsList = Collections.synchronizedList(new ArrayList<>());
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private long sessionDuration;


    public Session register(String username, String password, String role) {
        LOGGER.info("Starting of user registration.");
        String salt = UUID.randomUUID().toString();
        String hashedPassword = DigestUtils.md5Hex(password + salt);

        User user = new User();
        user.setLogin(username);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setRole(role);
        userService.add(user);
        return getSessionForUser(user);
    }

    private Session getSessionForUser(User user) {
        LOGGER.info("Start of getting the session for user.");
        if (user != null) {
            Session session = new Session();
            String uuid = UUID.randomUUID().toString();
            session.setToken(uuid);
            session.setUser(user);
            session.setExpireDate(LocalDateTime.now().plusSeconds(sessionDuration));
            sessionsList.add(session);
            LOGGER.info("The user session has been started: {}", session);
            return session;
        }
        return null;
    }

    public Session login(String userName, String password) {
        LOGGER.info("Starting of user authorization.");
        User user = auth(userName, password);
        if (user != null) {
            return getSessionForUser(user);
        }
        return null;
    }

    private User auth(String userName, String password) {
        LOGGER.info("Starting of user authentication.");
        User user = userService.getUser(userName);
        if (user != null) {
            String userHashedPassword = user.getPassword();
            String salt = user.getSalt();
            String incomingHashedPassword = DigestUtils.md5Hex(password + salt);
            if (userHashedPassword.equals(incomingHashedPassword)) {
                LOGGER.info("User has been authenticated successfully.");
                return user;
            } else {
                LOGGER.warn("User hasn't been authenticated.");
                return null;
            }
        }
        LOGGER.warn("User hasn't been authenticated.");
        return null;
    }

    public void logout(Session currentSession) {
        LOGGER.info("Starting of logout.");
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while (sessionIterator.hasNext()) {
            Session nextSession = sessionIterator.next();
            String token = nextSession.getToken();
            if (token.equals(currentSession.getToken())) {
                sessionIterator.remove();
                LOGGER.info("Session has been finished.");
                break;
            }
        }
    }

    public Session getSession(String token) {
        LOGGER.info("Start of getting the session.");
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while (sessionIterator.hasNext()) {
            Session session = sessionIterator.next();
            if (token.equals(session.getToken())) {
                if (LocalDateTime.now().isAfter(session.getExpireDate())) {
                    sessionIterator.remove();
                    LOGGER.warn("Session has been expired.");
                    return null;
                }
                LOGGER.info("Session is valid.");
                return session;
            }
        }
        return null;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSessionDuration(long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }


}

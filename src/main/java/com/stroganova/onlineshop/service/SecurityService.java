package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.entity.UserRole;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

public class SecurityService {

    private UserService userService;
    private List<Session> sessionsList = Collections.synchronizedList(new ArrayList<>());
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private long sessionDuration;


    public Optional<Session> register(String username, String password) {
        logger.info("Starting of user registration.");
        String salt = UUID.randomUUID().toString();
        String hashedPassword = DigestUtils.md5Hex(password + salt);

        User user = new User();
        user.setLogin(username);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setRole(UserRole.USER.getName());
        userService.add(user);
        return getSessionForUser(user);
    }

    private Optional<Session> getSessionForUser(User user) {
        logger.info("Start of getting the session for user.");
        Session session = new Session();
        String uuid = UUID.randomUUID().toString();
        session.setToken(uuid);
        session.setUser(user);
        session.setExpireDate(LocalDateTime.now().plusSeconds(sessionDuration));
        sessionsList.add(session);

        logger.info("The user session has been started: {}", session);
        logger.info("getSessionForUser: sessionsList size: {}", sessionsList.size());
        return Optional.of(session);
    }

    public Optional<Session> login(String userName, String password) {
        logger.info("Starting of user authorization.");
        User user = userService.getUser(userName);
        if (user != null) {
            String userHashedPassword = user.getPassword();
            String salt = user.getSalt();
            String incomingHashedPassword = DigestUtils.md5Hex(password + salt);
            if (userHashedPassword.equals(incomingHashedPassword)) {
                logger.info("User has been authorized successfully.");
                return getSessionForUser(user);
            } else {
                logger.warn("User hasn't been authorized.");
                return Optional.empty();
            }
        }
        logger.warn("User hasn't been authenticated.");
        return Optional.empty();
    }

    public void logout(Session currentSession) {
        logger.info("Starting of logout.");
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while (sessionIterator.hasNext()) {
            Session nextSession = sessionIterator.next();
            String token = nextSession.getToken();
            if (token.equals(currentSession.getToken())) {
                sessionIterator.remove();
                logger.info("Session has been finished.");
                break;
            }
        }
    }

    public Optional<Session> getSession(String token) {
        logger.info("Start of getting the session for token: {}.", token);
        logger.info("getSession by token, sessionsList size: {}", sessionsList.size());
        Iterator<Session> sessionIterator = sessionsList.iterator();
        while (sessionIterator.hasNext()) {
            Session session = sessionIterator.next();
            if (token.equals(session.getToken())) {
                if (LocalDateTime.now().isAfter(session.getExpireDate())) {
                    sessionIterator.remove();
                    logger.warn("Session has been expired.");
                    return Optional.empty();
                }
                logger.info("Session is valid.");
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSessionDuration(long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }


}

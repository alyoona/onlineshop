package com.stroganova.onlineshop.web.util;

import com.stroganova.onlineshop.entity.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class WebUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);

    public static Optional<String> getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if("user-token".equals(cookie.getName())) {
                    LOGGER.info("User-token cookie has been received.");
                    return Optional.of(cookie.getValue());
                }
            }
        }
        LOGGER.warn("There is no user-token cookie.");
        return Optional.empty();
    }

    public static Cookie getSessionCookie(Session session) {
        Cookie cookie = new Cookie("user-token", session.getToken());
        int sessionAge = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), session.getExpireDate());
        if(sessionAge > 0) {
            cookie.setMaxAge(sessionAge);
        } else {
            LOGGER.warn("Session has been expired, user-token cookie should be deleted.");
            cookie.setMaxAge(0);
        }
        return cookie;
    }
}

package com.stroganova.onlineshop.web.util;

import com.stroganova.onlineshop.entity.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WebUtil {

    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if("user-token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static Cookie getSessionCookie(Session session) {
        Cookie cookie = new Cookie("user-token", session.getToken());
        int sessionAge = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), session.getExpireDate());
        if(sessionAge > 0) {
            cookie.setMaxAge(sessionAge);
        } else {
            cookie.setMaxAge(0);
        }
        return cookie;
    }
}

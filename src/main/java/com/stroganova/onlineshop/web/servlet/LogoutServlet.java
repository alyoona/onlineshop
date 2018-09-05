package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

   private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("user-token")) {
                User user = userService.getUser(cookie.getValue());

                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setPath("/");

                if(user != null) {
                    user.setUserToken(cookie.getValue());
                    userService.setUserToken(user);
                }

                response.addCookie(cookie);

                ServletContext sc = getServletContext();
                sc.removeAttribute("authorizedUser");

                break;
            }
        }
        response.sendRedirect("/products");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

package com.stroganova.onlineshop.web.controller;


import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginPage() {
        LOGGER.info("Login form should be displayed.");
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        LOGGER.info("Start of processing the POST request by LoginServlet");

        Optional<Session> session = securityService.login(username, password);

        if (session.isPresent()) {
            LOGGER.info("User has been authorized successfully and redirected to main page.");
            response.addCookie(WebUtil.getSessionCookie(session.get()));
            return "redirect:/products";
        } else {
            LOGGER.warn("User has not been authorized, login form should be displayed again.");
            return "redirect:/login";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(@RequestAttribute("session") Session session) {
        LOGGER.info("Start of processing the POST request by LogoutServlet");
        securityService.logout(session);
        LOGGER.info("User has been logged out.");
        return "redirect:/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String registerPage() {
        LOGGER.info("Register form should be displayed.");
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        LOGGER.info("Start of processing the POST request by RegisterServlet");
        Optional<Session> session = securityService.register(login, password);
        if (session.isPresent()) {
            LOGGER.info("User has been registered successfully and redirected to main page.");
            response.addCookie(WebUtil.getSessionCookie(session.get()));
            return "redirect:/products";
        } else {
            LOGGER.warn("User has not been registered, register form should be displayed again.");
            return "redirect:/register";
        }
    }


}

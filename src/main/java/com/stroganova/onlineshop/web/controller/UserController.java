/*
package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.service.impl.SecurityServiceDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private SecurityService securityService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserController(SecurityServiceDefault securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginPage() {
        LOGGER.info("Login form should be displayed.");
        return "login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String registerPage() {
        LOGGER.info("Register form should be displayed.");
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String login, @RequestParam String password) {
        LOGGER.info("Start of processing the POST request by RegisterServlet");
        securityService.register(login, password);
        return "redirect:/products";
    }

}
*/

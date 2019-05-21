package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.entity.UserRole;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.service.impl.SecurityServiceDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private SecurityService securityService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserController(SecurityServiceDefault securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestParam String username,  @RequestParam String password) {
        LOGGER.info("Start of processing the POST request by RegisterServlet");

        User user = securityService.register(username, password);
        return new ResponseEntity<>(user.getRole(), HttpStatus.CREATED);
    }

}

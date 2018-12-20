package com.stroganova.onlineshop.controller;


import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
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
    @ResponseBody
    public String openLoginForm()  {
        LOGGER.info("Start of processing the GET request by LoginServlet");
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        LOGGER.info("Login form should be displayed.");
        return page;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public void doLogin(@RequestParam(name="username") String username
            , @RequestParam(name="password") String password
            , HttpServletResponse response) throws IOException {
        LOGGER.info("Start of processing the POST request by LoginServlet");

        Optional<Session> session = securityService.login(username, password);

        if (session.isPresent()) {
            LOGGER.info("User has been authorized successfully and redirected to main page.");
            response.addCookie(WebUtil.getSessionCookie(session.get()));
            response.sendRedirect("/products");
        } else {
            LOGGER.warn("User has not been authorized, login form should be displayed again.");
            response.sendRedirect("/login");
        }
    }
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void doLogout(@RequestAttribute("session") Session session, HttpServletResponse response) throws IOException {
        LOGGER.info("Start of processing the POST request by LogoutServlet");
        securityService.logout(session);
        response.sendRedirect("/login");
        LOGGER.info("User has been logged out.");
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    @ResponseBody
    public String openRegisterForm() {
        LOGGER.info("Start of processing the GET request by RegisterServlet");
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("register.html");
        LOGGER.info("Register form should be displayed.");
        return page;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void doRegister(@RequestParam(name="login") String username
            , @RequestParam(name="password") String password
            , HttpServletResponse response) throws IOException {
        LOGGER.info("Start of processing the POST request by RegisterServlet");
        Optional<Session> session = securityService.register(username, password);
        if (session.isPresent()) {
            LOGGER.info("User has been registered successfully and redirected to main page.");
            response.addCookie(WebUtil.getSessionCookie(session.get()));
            response.sendRedirect("/products");
        } else {
            LOGGER.warn("User has not been registered, register form should be displayed again.");
            response.sendRedirect("/register");
        }
    }

}
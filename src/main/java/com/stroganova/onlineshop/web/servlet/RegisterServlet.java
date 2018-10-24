package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import com.stroganova.onlineshop.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegisterServlet extends HttpServlet {

    private SecurityService securityService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start of processing the GET request by RegisterServlet");
        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("register.html");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
        logger.info("Register form should be displayed.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start of processing the POST request by RegisterServlet");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Optional<Session> session = securityService.register(login, password);

        if (session.isPresent()) {
            logger.info("User has been registered successfully and redirected to main page.");
            response.addCookie(WebUtil.getSessionCookie(session.get()));
            response.sendRedirect("/");
        } else {
            logger.warn("User has not been registered, register form should be displayed again.");
            response.sendRedirect("/register");
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}

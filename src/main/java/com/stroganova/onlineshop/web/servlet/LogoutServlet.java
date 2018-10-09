package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private SecurityService securityService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Start of processing the POST request by LogoutServlet");
        Session session = (Session) request.getAttribute("session");
        securityService.logout(session);
        response.sendRedirect("/login");
        LOGGER.info("User has been logged out.");
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import com.stroganova.onlineshop.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginServlet extends HttpServlet {

    private SecurityService securityService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start of processing the GET request by LoginServlet");
        Map<String, Object> pageVariables = new HashMap<>();

        Session session = (Session) request.getAttribute("session");
        pageVariables.put("session", session);
        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("login.html", pageVariables);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
        logger.info("Login form should be displayed.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start of processing the POST request by LoginServlet");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<Session> session = securityService.login(username, password);

        if (session.isPresent()) {
            logger.info("User has been authorized successfully and redirected to main page.");
            response.addCookie(WebUtil.getSessionCookie(session.get()));
            response.sendRedirect("/");
        } else {
           logger.warn("User has not been authorized, login form should be displayed again.");
           response.sendRedirect("/login");
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import com.stroganova.onlineshop.web.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();

        String token = WebUtil.getToken(request);
        Session session = securityService.getSession(token);
        if (session != null) {
            pageVariables.put("session", session);
        }

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("login.html", pageVariables);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Session session = securityService.auth(login, password);

        if (session != null) {
            response.addCookie(WebUtil.getSessionCookie(session));
            response.sendRedirect("/");
        } else {
           response.sendRedirect("/login");
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import com.stroganova.onlineshop.web.util.WebUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("register.html");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

            Session session = securityService.register(login, password);

            if (session != null) {
                response.addCookie(WebUtil.getSessionCookie(session));
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/register");
            }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}

package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.util.WebUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = WebUtil.getToken(request);
        if (token != null) {
            securityService.logout(token);

            System.out.println(securityService.toString());

            response.sendRedirect("/login");
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

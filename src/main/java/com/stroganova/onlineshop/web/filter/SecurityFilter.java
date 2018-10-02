package com.stroganova.onlineshop.web.filter;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = WebUtil.getToken(request);
        boolean isAuth = false;

        if(token != null) {
            Session session = securityService.getSession(token);
            if (session != null) {
                request.setAttribute("session", session);
                filterChain.doFilter(servletRequest, servletResponse);
                isAuth = true;
            }
        }

        if(!isAuth) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/login");
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}


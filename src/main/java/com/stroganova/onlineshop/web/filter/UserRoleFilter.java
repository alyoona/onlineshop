package com.stroganova.onlineshop.web.filter;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Session session = (Session) request.getAttribute("session");
        User user = session.getUser();
        String role = user.getRole();

        if ("user".equals(role)) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}

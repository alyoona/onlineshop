package com.stroganova.onlineshop.web.filter;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRoleFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LOGGER.info("UserRoleFilter is started by requested URI: {}. ", request.getRequestURI());
        Session session = (Session) request.getAttribute("session");
        User user = session.getUser();
        String role = user.getRole();

        if ("user".equals(role)) {
            LOGGER.info("User has been redirected to main page.");
            LOGGER.warn("User's role \"{}\" doesn't provide access to {}", role, request.getRequestURI());
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/");

        } else {
            LOGGER.info("The user has access to the resource.");
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

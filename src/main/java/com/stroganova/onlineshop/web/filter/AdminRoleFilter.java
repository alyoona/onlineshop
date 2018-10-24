package com.stroganova.onlineshop.web.filter;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminRoleFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("AdminRoleFilter is started by requested URI: {}. ", request.getRequestURI());
        Session session = (Session) request.getAttribute("session");
        User user = session.getUser();
        String role = user.getRole();

        if (UserRole.ADMIN.getName().equalsIgnoreCase(role)) {
            logger.info("The user has the access to the resource.");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.info("User has been redirected to main page.");
            logger.warn("User's role \"{}\" doesn't provide access to {}", role, request.getRequestURI());
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/");
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}

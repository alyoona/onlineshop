package com.stroganova.onlineshop.web.filter;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        LOGGER.info("SecurityFilter is started by requested URI {}. ", request.getRequestURI());
        String token = WebUtil.getToken(request);

        boolean isAuth = false;

        if (token != null) {
            MDC.put("sessionId", token);
            Session session = securityService.getSession(token);
            if (session != null) {
                isAuth = true;
                request.setAttribute("session", session);
            }
        }

        if (isAuth) {
            try {
                LOGGER.info("Go to the next filter or servlet.");
                filterChain.doFilter(servletRequest, servletResponse);
            } finally {
                MDC.remove("sessionId");
            }
        } else {
            LOGGER.warn("There is no active session.");
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


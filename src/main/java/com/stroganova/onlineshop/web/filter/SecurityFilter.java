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
import java.util.Optional;

public class SecurityFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        logger.info("SecurityFilter is started by requested URI {}. ", request.getRequestURI());
        Optional<String> token = WebUtil.getToken(request);

        boolean isAuth = false;

        if (token.isPresent()) {
            MDC.put("sessionId", token.get());
            Optional<Session> session = securityService.getSession(token.get());
            if (session.isPresent()) {
                isAuth = true;
                request.setAttribute("session", session.get());
            }
        }

        if (isAuth) {
            try {
                logger.info("Go to the next filter or servlet.");
                filterChain.doFilter(servletRequest, servletResponse);
            } finally {
                MDC.remove("sessionId");
            }
        } else {
            logger.warn("There is no active session.");
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


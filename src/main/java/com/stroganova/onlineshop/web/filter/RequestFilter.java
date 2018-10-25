package com.stroganova.onlineshop.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

public class RequestFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        logger.info("RequestFilter is started.");
        MDC.put("requestId", UUID.randomUUID().toString());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info("doFilter of RequestFilter is done.");
        } finally {
            MDC.remove("requestId");
            logger.info("MDCRequestId is removed.");
        }
        logger.info("End of RequestFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

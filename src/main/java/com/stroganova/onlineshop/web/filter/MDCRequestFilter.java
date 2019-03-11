package com.stroganova.onlineshop.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(value = MDCRequestFilter.MDC_FILTER_ORDER)
public class MDCRequestFilter implements Filter {
    /*SecurityProperties.DEFAULT_FILTER_ORDER = -100
    MDCRequestFilter is expected to be the first in the filters chain*/
    static final int MDC_FILTER_ORDER = -200;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        logger.info("MDCRequestFilter is started.");
        MDC.put("requestId", UUID.randomUUID().toString());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info("doFilter of MDCRequestFilter is done.");
        } finally {
            MDC.remove("requestId");
            logger.info("MDCRequestId is removed.");
        }
        logger.info("End of MDCRequestFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

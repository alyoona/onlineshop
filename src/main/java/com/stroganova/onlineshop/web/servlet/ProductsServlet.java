package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.locator.ServiceLocator;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductsServlet extends HttpServlet {

    private ProductService productService = ServiceLocator.getService(ProductService.class);
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start of processing the GET request by ProductsServlet");
        Map<String, Object> pageVariables = new HashMap<>();

        Session session = (Session) request.getAttribute("session");
        pageVariables.put("session", session);

        pageVariables.put("products", productService.getAll());

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("products.html", pageVariables);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
        logger.info("All products should be displayed.");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}

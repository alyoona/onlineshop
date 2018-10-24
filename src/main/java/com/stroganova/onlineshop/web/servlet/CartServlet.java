package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.entity.Session;
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

public class CartServlet extends HttpServlet {

    private ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start of processing the GET request by CartServlet");
        Map<String, Object> pageVariables = new HashMap<>();

        Session session = (Session) request.getAttribute("session");
        pageVariables.put("cart", session.getCart());

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("cart.html", pageVariables);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
        logger.info("User's cart should be displayed, {}", session);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start of processing the POST request by CartServlet");
        long id = Long.parseLong(request.getParameter("id"));
        Product product = productService.getProduct(id);
        Session session = (Session) request.getAttribute("session");
        session.addToCart(product);
        response.sendRedirect("/products");
        logger.info("All products should be displayed after adding the product to cart.");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}

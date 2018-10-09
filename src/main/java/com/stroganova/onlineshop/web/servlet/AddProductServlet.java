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

public class AddProductServlet extends HttpServlet {

    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Start of processing the GET request by AddProductServlet");
        Map<String, Object> pageVariables = new HashMap<>();
        Session session = (Session) request.getAttribute("session");
        pageVariables.put("session", session);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("add.html", pageVariables));
        LOGGER.info("End of processing the GET request by AddProductServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Start of processing the POST request by AddProductServlet");
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String picturePath = request.getParameter("picturePath");
        String description = request.getParameter("description");

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        newProduct.setPicturePath(picturePath);

        productService.add(newProduct);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect("/products/add");
        LOGGER.info("End of processing the POST request by AddProductServlet");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}

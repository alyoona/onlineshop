package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.web.pages.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductsServlet extends HttpServlet{

    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", productService.getAll());

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("products.html", pageVariables);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }




}

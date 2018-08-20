package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.web.pages.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("add.html"));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    }

    public void setProductService(ProductService productService) {
    this.productService = productService;
    }

}

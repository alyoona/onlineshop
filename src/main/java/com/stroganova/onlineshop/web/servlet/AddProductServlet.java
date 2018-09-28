package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.SecurityService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import com.stroganova.onlineshop.web.util.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends HttpServlet {

    private ProductService productService;
    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String token = WebUtil.getToken(request);
        Session session = securityService.getSession(token);
        if (session != null) {
            pageVariables.put("session", session);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("add.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String token = WebUtil.getToken(request);

        Session session = securityService.getSession(token);

        if (session != null) {

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
        } else {
            response.sendRedirect("/login");
        }
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.UserService;
import com.stroganova.onlineshop.web.templater.PageGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends HttpServlet {

    private ProductService productService;
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userLogin = userService.getAuthorizedUserLogin(request);
        boolean isAuthorized = false;
        Map<String, Object> pageVariables = new HashMap<>();
        if (userLogin != null) {
            pageVariables.put("authorizedUser", userLogin);
            isAuthorized = true;
        }

        if(isAuthorized) {

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(PageGenerator.instance().getPage("add.html", pageVariables));
        } else {
            request.setAttribute("accessError", "Adding product is available after authorization");
            request.setAttribute("addProduct", true);
            RequestDispatcher rs = request.getRequestDispatcher("/login");
            rs.include(request, response);
        }
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
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

package com.stroganova.onlineshop.web.servlet;

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

public class ProductsServlet extends HttpServlet{

    private ProductService productService;

    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        String userLogin = userService.getAuthorizedUserLogin(request);
        boolean isAuthorized = false;
        if (userLogin != null) {
            pageVariables.put("authorizedUser", userLogin);
            isAuthorized = true;
        }
        if(isAuthorized) {

            pageVariables.put("products", productService.getAll());

            PageGenerator pageGenerator = PageGenerator.instance();

            String page = pageGenerator.getPage("products.html", pageVariables);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(page);
        } else {
            request.setAttribute("accessError", "Products are available after authorization");
            request.setAttribute("getProducts", true);
            RequestDispatcher rs = request.getRequestDispatcher("/login");
            rs.include(request, response);
        }
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }




}

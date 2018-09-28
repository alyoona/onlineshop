package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.entity.Session;
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

public class CartServlet extends HttpServlet {

    private ProductService productService;
    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        String token = WebUtil.getToken(request);
        Session session = securityService.getSession(token);
        if(session != null) {
            pageVariables.put("cart", session.getCart());
        }
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("cart.html", pageVariables);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Product product = productService.getProduct(id);
        String token = WebUtil.getToken(request);
        Session session = securityService.getSession(token);
        session.addToCart(product);
        response.sendRedirect("/products");
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}

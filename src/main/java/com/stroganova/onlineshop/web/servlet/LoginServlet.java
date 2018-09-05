package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.service.UserService;
import com.stroganova.onlineshop.web.templater.PageGenerator;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

   private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("targetUriPageVariable", request.getRequestURI());

        pageVariables.put("accessError", request.getAttribute("accessError"));
        pageVariables.put("getProducts", request.getAttribute("getProducts"));
        pageVariables.put("addProduct", request.getAttribute("addProduct"));

        ServletContext sc = getServletContext();

        /*Don't display Login Form for authorized user*/
        pageVariables.put("authorizedUser", sc.getAttribute("authorizedUser"));

        /*if user enter incorrect login/password*/
        pageVariables.put("authenticationError", sc.getAttribute("authenticationError"));
        sc.removeAttribute("authenticationError");

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html", pageVariables);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String targetUri = request.getParameter("targetUriParameterName");
        ServletContext sc = getServletContext();

        if (userService.isIdentified(login, password)) {
            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user-token", uuid);

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setUserToken(cookie.getValue());
            userService.setUserToken(user);

            sc.setAttribute("authorizedUser", login);

            response.addCookie(cookie);
            response.sendRedirect(targetUri);
        } else {
            sc.setAttribute("authenticationError", true);
            response.sendRedirect(targetUri);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

package com.stroganova.onlineshop.web.servlet;

import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.service.UserService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("loginIsExistError", request.getParameter("loginIsExistError"));

        /*Don't display Register Form for authorized user*/
        pageVariables.put("authorizedUser", userService.getAuthorizedUserLogin(request));

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("register.html", pageVariables);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        if (userService.isIdentified(login)) {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("loginIsExistError", login);

            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("register.html", pageVariables);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(page);

        } else {
            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user-token", uuid);

            String password = request.getParameter("password");

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setUserToken(cookie.getValue());
            userService.add(user);

            response.addCookie(cookie);
            response.sendRedirect("/register");
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}

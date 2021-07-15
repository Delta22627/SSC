package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.service.User;
import io.muzoo.ssc.webapp.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends AbstractRoutableHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            String username = securityService.getCurrentUsername(request);
            request.setAttribute("username", username);

            UserService userService = UserService.getInstance();

            request.setAttribute("users", userService.findAll());

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/home.jsp");
            requestDispatcher.include(request, response);
        } else {
            response.sendRedirect("/login");
        }

    }

    @Override
    public String getPattern() {
        return "/index.jsp";
    }
}
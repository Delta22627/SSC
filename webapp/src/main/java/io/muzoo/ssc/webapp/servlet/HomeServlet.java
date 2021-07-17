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
            String username = (String) request.getSession().getAttribute("username");
            UserService userService = UserService.getInstance();


            request.setAttribute("currentUser", userService.findByUsername(username));
            request.setAttribute("users", userService.findAll());

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/home.jsp");
            requestDispatcher.include(request, response);
            request.getSession().removeAttribute("hasError");
            request.getSession().removeAttribute("message");


        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }

    }

    @Override
    public String getPattern() {
        return "/index.jsp";
    }
}

package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.service.UserService;
import io.muzoo.ssc.webapp.service.UserServiceException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends AbstractRoutableHttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (securityService.isAuthorized(request)) {

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/create.jsp");
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
        return "/user/create";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            String username = StringUtils.trim((String) request.getParameter("username"));
            String displayName = StringUtils.trim((String) request.getParameter("displayName"));
            String password = (String) request.getParameter("password");
            String cpassword = (String) request.getParameter("cpassword");

            UserService userService = UserService.getInstance();
            String errorMessage = null;
            if (userService.findByUsername(username) != null){
                errorMessage = String.format("Username %s already been taken.", username);
            }

            if (StringUtils.isBlank(displayName)){
                errorMessage = "Display name cannot be blank";
            }

            if (!StringUtils.equals(password,cpassword)){
                errorMessage = "Confirm password is not the same as password";
            }

            if(errorMessage != null){
                request.getSession().setAttribute("hasError", true);
                request.getSession().setAttribute("message", errorMessage);
            }
            else {
                try {
                    userService.createUser(username, password, displayName);
                    request.getSession().setAttribute("hasError", false);
                    request.getSession().setAttribute("message", String.format("Username %s created.", username));
                    response.sendRedirect("/");
                    return;
                } catch (Exception e) {
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", String.format("Username %s already been taken.", username));
                }
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/create.jsp");
            requestDispatcher.include(request, response);



        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }

    }
}

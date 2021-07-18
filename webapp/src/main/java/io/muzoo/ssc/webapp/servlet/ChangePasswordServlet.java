package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.service.User;
import io.muzoo.ssc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePasswordServlet extends AbstractRoutableHttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (securityService.isAuthorized(request)) {
            String username = StringUtils.trim((String) request.getParameter("username")); // from query part
            UserService userService = UserService.getInstance();

            User user = userService.findByUsername(username);
            request.setAttribute("user", user);
            request.setAttribute("username", user.getUsername());



            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/password.jsp");
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
        return "/user/password";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            String username = StringUtils.trim((String) request.getParameter("username")); // from query part
            String password = (String) request.getParameter("password");
            String cpassword = (String) request.getParameter("cpassword");

            UserService userService = UserService.getInstance();
            User user = userService.findByUsername(username);

            String errorMessage = null;
            if (user == null){
                errorMessage = String.format("Username %s does not exist.", username);
            }

            if (StringUtils.isBlank(password)){
                errorMessage = "New password cannot be blank";
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
                    userService.changePassword(username, password);
                    request.getSession().setAttribute("hasError", false);
                    request.getSession().setAttribute("message", String.format("User %s password has been updated.", username));
                    response.sendRedirect("/");
                    return;
                } catch (Exception e) {
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", String.format("Other Error.", username));
                }
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/password.jsp");
            requestDispatcher.include(request, response);



        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }

    }
}

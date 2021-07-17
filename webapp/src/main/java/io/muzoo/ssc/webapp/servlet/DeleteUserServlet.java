package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.service.User;
import io.muzoo.ssc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserServlet extends AbstractRoutableHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            String username = (String) request.getSession().getAttribute("username");
            UserService userService = UserService.getInstance();

            try {
                User currentUser = userService.findByUsername(username);
                User deletingUser =  userService.findByUsername(request.getParameter("username"));
                if (StringUtils.equals(currentUser.getUsername(),deletingUser.getUsername())){
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", "You cannot delete your own account.");
                }else if (userService.deleteUserByUsername(deletingUser.getUsername())){
                    // go home with success message
                    request.getSession().setAttribute("hasError", false);
                    request.getSession().setAttribute("message", String.format("User %s is successfully deleted.", deletingUser.getUsername()));
                }else {
                    // go home with error delete message
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", String.format("Unable to delete user %s.", deletingUser.getUsername()));
                }
            } catch (Exception e){
                // go home with error delete message
                request.getSession().setAttribute("hasError", true);
                request.getSession().setAttribute("message", String.format("Unable to delete user %s.", request.getParameter("username")));
            }


            response.sendRedirect("/");

        } else {
            response.sendRedirect("/login");
        }

    }

    @Override
    public String getPattern() {
        return "/user/delete";
    }
}

package io.muzoo.ssc.webapp.service;

import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Objects;

public class SecurityService {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getCurrentUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object usernameObject = session.getAttribute("username");
        return (String) usernameObject;
    }

    public boolean isAuthorized(HttpServletRequest request) {
        String username = (String) request.getSession()
                .getAttribute("username");
        return (username != null && userService.findByUsername(username) != null);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.invalidate();
    }

    public boolean login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.findByUsername(username);
        try{
            Boolean correctPass = BCrypt.checkpw(password, user.getPassword());
            if (user != null && correctPass) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

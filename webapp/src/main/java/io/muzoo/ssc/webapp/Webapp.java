package io.muzoo.ssc.webapp;

import io.muzoo.ssc.webapp.service.DatabaseConnectionService;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import io.muzoo.ssc.webapp.servlet.ServletRouter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;

import java.io.File;
import javax.servlet.ServletException;

public class Webapp {

    public static void main(String[] args) {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(80);

        File docBase = new File("src/main/webapp/");
        docBase.mkdirs();


        SecurityService securityService = new SecurityService();
        securityService.setUserService(UserService.getInstance());


        try {
            Context ctx = tomcat.addWebapp("", docBase.getAbsolutePath());

            ServletRouter servletRouter = new ServletRouter();
            servletRouter.init(ctx);
            ErrorPage error404Page = new ErrorPage();
            error404Page.setErrorCode(404);
            error404Page.setLocation("/WEB-INF/error404.jsp");
            ctx.addErrorPage(error404Page);

            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }
}

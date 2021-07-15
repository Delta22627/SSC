package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.service.SecurityService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractRoutableHttpServlet extends HttpServlet implements Routable {

    protected SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

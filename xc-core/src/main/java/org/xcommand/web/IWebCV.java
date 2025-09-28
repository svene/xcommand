package org.xcommand.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IWebCV {

    HttpServletRequest getRequest();

    HttpServletResponse getResponse();

    String getHttpMethod();

    ServletContext getServletContext();

    void setRequest(HttpServletRequest aRequest);

    void setResponse(HttpServletResponse aResponse);

    void setHttpMethod(String aMethod);

    void setServletContext(ServletContext aServletContext);
}

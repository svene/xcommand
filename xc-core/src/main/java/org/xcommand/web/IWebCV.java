package org.xcommand.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IWebCV
{

// --- Access ---

	HttpServletRequest getRequest();
	HttpServletResponse getResponse();
	String getHttpMethod();
	ServletContext getServletContext();

// --- Setting ---

	void setRequest(HttpServletRequest aRequest);
	void setResponse(HttpServletResponse aResponse);
	void setHttpMethod(String aMethod);
	void setServletContext(ServletContext aServletContext);
}

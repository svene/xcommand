package org.xcommand.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IWebCV
{

// --- Access ---

	public HttpServletRequest getRequest();
	public HttpServletResponse getResponse();
	public String getHttpMethod();
	public ServletContext getServletContext();

// --- Setting ---

	public void setRequest(HttpServletRequest aRequest);
	public void setResponse(HttpServletResponse aResponse);
	public void setHttpMethod(String aMethod);
	public void setServletContext(ServletContext aServletContext);
}

package org.xcommand.web;

import org.xcommand.core.TCP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

public class WebCV
{

// --- Access ---

	public static HttpServletRequest getRequest()
	{

		return (HttpServletRequest) TCP.getContext().get(KEY_REQUEST);
	}

	public static HttpServletResponse getResponse()
	{
		return (HttpServletResponse) TCP.getContext().get(KEY_RESPONSE);
	}

	public static String getHttpMethod()
	{
		return (String) TCP.getContext().get(KEY_HTTP_METHOD);
	}

	public static ServletContext getServletContext()
	{
		return (ServletContext) TCP.getContext().get(KEY_SERVLET_CONTEXT);
	}

// --- Setting ---

	public static void setRequest(HttpServletRequest aRequest)
	{
		TCP.getContext().put(KEY_REQUEST, aRequest);
	}

	public static void setResponse(HttpServletResponse aResponse)
	{
		TCP.getContext().put(KEY_RESPONSE, aResponse);
	}

	public static void setHttpMethod(String aMethod)
	{
		TCP.getContext().put(KEY_HTTP_METHOD, aMethod);
	}

	public static void setServletContext(ServletContext aServletContext)
	{
		TCP.getContext().put(KEY_SERVLET_CONTEXT, aServletContext);
	}

// --- Implementation ---

	public static final String NS = "org.xcommand.web.";
	public static final String KEY_REQUEST = NS + "request";
	public static final String KEY_RESPONSE = NS + "response";
	public static final String KEY_SERVLET_CONTEXT = NS + "servletContext";

	public static final String KEY_HTTP_METHOD = NS + "http_method";
	public static final String VALUE_HTTP_METHOD_HEAD = NS + "HEAD";
	public static final String VALUE_HTTP_METHOD_GET = NS + "GET";
	public static final String VALUE_HTTP_METHOD_PUT = NS + "PUT";
	public static final String VALUE_HTTP_METHOD_DELETE = NS + "DELETE";

}
package org.xcommand.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.util.Map;

public class WebContextView
{

// --- Access ---

	public static HttpServletRequest getRequest(Map aContext)
	{
		return (HttpServletRequest) aContext.get(KEY_REQUEST);
	}

	public static HttpServletResponse getResponse(Map aContext)
	{
		return (HttpServletResponse) aContext.get(KEY_RESPONSE);
	}

	public static String getHttpMethod(Map aContext)
	{
		return (String) aContext.get(KEY_HTTP_METHOD);
	}

	public static ServletContext getServletContext(Map aContext)
	{
		return (ServletContext) aContext.get(KEY_SERVLET_CONTEXT);
	}

// --- Setting ---

	public static void setRequest(Map aContext, HttpServletRequest aRequest)
	{
		aContext.put(KEY_REQUEST, aRequest);
	}

	public static void setResponse(Map aContext, HttpServletResponse aResponse)
	{
		aContext.put(KEY_RESPONSE, aResponse);
	}

	public static void setHttpMethod(Map aContext, String aMethod)
	{
		aContext.put(KEY_HTTP_METHOD, aMethod);
	}

	public static void setServletContext(Map aContext, ServletContext aServletContext)
	{
		aContext.put(KEY_SERVLET_CONTEXT, aServletContext);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.web.";
	private static final String KEY_REQUEST = NS + "request";
	private static final String KEY_RESPONSE = NS + "response";
	private static final String KEY_SERVLET_CONTEXT = NS + "servletContext";

	private static final String KEY_HTTP_METHOD = NS + "http_method";
	public static final String VALUE_HTTP_METHOD_HEAD = NS + "HEAD";
	public static final String VALUE_HTTP_METHOD_GET = NS + "GET";
	public static final String VALUE_HTTP_METHOD_PUT = NS + "PUT";
	public static final String VALUE_HTTP_METHOD_DELETE = NS + "DELETE";

}
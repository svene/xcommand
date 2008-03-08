package org.xcommand.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.util.Map;

public class WebXCV
{

// --- Access ---

	public static HttpServletRequest getRequest(Map aCtx)
	{
		return (HttpServletRequest) aCtx.get(KEY_REQUEST);
	}

	public static HttpServletResponse getResponse(Map aCtx)
	{
		return (HttpServletResponse) aCtx.get(KEY_RESPONSE);
	}

	public static String getHttpMethod(Map aCtx)
	{
		return (String) aCtx.get(KEY_HTTP_METHOD);
	}

	public static ServletContext getServletContext(Map aCtx)
	{
		return (ServletContext) aCtx.get(KEY_SERVLET_CONTEXT);
	}

// --- Setting ---

	public static void setRequest(Map aCtx, HttpServletRequest aRequest)
	{
		aCtx.put(KEY_REQUEST, aRequest);
	}

	public static void setResponse(Map aCtx, HttpServletResponse aResponse)
	{
		aCtx.put(KEY_RESPONSE, aResponse);
	}

	public static void setHttpMethod(Map aCtx, String aMethod)
	{
		aCtx.put(KEY_HTTP_METHOD, aMethod);
	}

	public static void setServletContext(Map aCtx, ServletContext aServletContext)
	{
		aCtx.put(KEY_SERVLET_CONTEXT, aServletContext);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.web.WebXCV.";
	public static final String KEY_REQUEST = NS + "request";
	public static final String KEY_RESPONSE = NS + "response";
	public static final String KEY_SERVLET_CONTEXT = NS + "servletContext";

	public static final String KEY_HTTP_METHOD = NS + "http_method";
	public static final String VALUE_HTTP_METHOD_HEAD = NS + "HEAD";
	public static final String VALUE_HTTP_METHOD_GET = NS + "GET";
	public static final String VALUE_HTTP_METHOD_PUT = NS + "PUT";
	public static final String VALUE_HTTP_METHOD_DELETE = NS + "DELETE";

}
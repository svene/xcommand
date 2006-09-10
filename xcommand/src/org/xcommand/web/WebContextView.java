package org.xcommand.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

// --- Implementation ---

	private static final String KEY_NAMESPACE = "webapp:";
	private static final String KEY_REQUEST = KEY_NAMESPACE + "request";
	private static final String KEY_RESPONSE = KEY_NAMESPACE + "response";
	private static final String KEY_HTTP_METHOD = KEY_NAMESPACE + "http_method";
	public static final String VALUE_HTTP_METHOD_HEAD = KEY_NAMESPACE + "HEAD";
	public static final String VALUE_HTTP_METHOD_GET = KEY_NAMESPACE + "GET";
	public static final String VALUE_HTTP_METHOD_PUT = KEY_NAMESPACE + "PUT";
	public static final String VALUE_HTTP_METHOD_DELETE = KEY_NAMESPACE + "DELETE";

}
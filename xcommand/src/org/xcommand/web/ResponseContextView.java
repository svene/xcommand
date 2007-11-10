package org.xcommand.web;

import java.util.Map;

public class ResponseContextView
{

// --- Access ---

	public static Integer getHttpErrorCode(Map aCtx)
	{
		return (Integer) aCtx.get(KEY_HTTP_ERROR_CODE);
	}

	public static String getHttpErrorMessage(Map aCtx)
	{
		return (String) aCtx.get(KEY_HTTP_ERROR_MESSAGE);
	}


// --- Setting ---

	public static void setHttpErrorCode(Map aCtx, Integer aHttpErrorCode)
	{
		aCtx.put(KEY_HTTP_ERROR_CODE, aHttpErrorCode);
	}

	public static void setHttpErrorMessage(Map aCtx, String aHttpErrorMessage)
	{
		aCtx.put(KEY_HTTP_ERROR_MESSAGE, aHttpErrorMessage);
	}

// --- Constants ---

	public static final String NS = "org.xcommand.web.ResponseContextView.";
	public static final String KEY_HTTP_ERROR_CODE = NS + "HTTP_ERROR_CODE";
	public static final String KEY_HTTP_ERROR_MESSAGE = NS + "HTTP_ERROR_MESSAGE";
}

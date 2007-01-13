package org.xcommand.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class XCRequestAttributeCV
{

// --- Access ---

	public static Map getXcContext(HttpServletRequest aRequest)
	{
		return (Map) aRequest.getAttribute(KEY_XC_CONTEXT);
	}

// --- Setting ---

	public static void setXcContext(HttpServletRequest aRequest, Map aCtx)
	{
		aRequest.setAttribute(KEY_XC_CONTEXT, aCtx);
	}

// --- Constants ---

	public static final String NS = "org.xcommand.web.XCRequestAttributeCV.";
	public static final String KEY_XC_CONTEXT = NS + "XC_CONTEXT";
}

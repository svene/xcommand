package org.xcommand.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class XCRequestAttributeCV
{
	public static final String KEY_XC_CONTEXT = "xc-context";

	public static Map getXcContext(HttpServletRequest aRequest)
	{
		return (Map) aRequest.getAttribute(KEY_XC_CONTEXT);
	}

	public static void setXcContext(HttpServletRequest aRequest, Map aCtx)
	{
		aRequest.setAttribute(KEY_XC_CONTEXT, aCtx);
	}

}

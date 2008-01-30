package org.xcommand.web;

import org.xcommand.core.TCP;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Filter responsible for creating a XC-Context and associating it with the request as an attribute 
 */
public class XCContextCreationFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException
	{
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException
	{
		// Birth of XC Context:
		Map ctx = new HashMap();
		if (useRequestForContext) // Attach XC Context to servletRequest as attribute:
		{
			XCRequestAttributeCV.setXcContext((HttpServletRequest) servletRequest, ctx);
		}
		if (useTreadForContext) // Attach XC Context to Thread:
		{
			// Not necessary to do anything
			// Use `TCP.getContext()' later to get the context from the current thread
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{

	}


	public void setUseTreadForContext(boolean aUseTreadForContext)
	{
		useTreadForContext = aUseTreadForContext;
	}

	public void setUseRequestForContext(boolean aUseRequestForContext)
	{
		useRequestForContext = aUseRequestForContext;
	}

	private ServletContext servletContext;
	boolean useTreadForContext = true;
	boolean useRequestForContext = false;
}

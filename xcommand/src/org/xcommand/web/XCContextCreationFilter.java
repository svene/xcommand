package org.xcommand.web;

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
		// Attach XC Context to servletRequest as attribute:
		XCRequestAttributeCV.setXcContext((HttpServletRequest) servletRequest, ctx);

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{

	}

	ServletContext servletContext;
}

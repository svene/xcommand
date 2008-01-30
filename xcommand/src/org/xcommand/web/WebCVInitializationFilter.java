package org.xcommand.web;

import org.xcommand.core.TCP;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Filter responsible for initializing the XC Context with webapp-related information (request, response, servletContext)
 */
public class WebCVInitializationFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException
	{
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aFilterChain)
		throws IOException, ServletException
	{
		// Retrieve XC Context:
		//Map ctx = XCRequestAttributeCV.getXcContext((HttpServletRequest) aRequest);
		Map ctx = TCP.getContext();

		// Populate XC Context with webapp-related information (request, response, servletContext):
		WebContextView.setRequest(ctx, (HttpServletRequest) aRequest);
		WebContextView.setResponse(ctx, (HttpServletResponse) aResponse);
		WebContextView.setServletContext(ctx, servletContext);
		System.out.println("XCServletFilter.doFilter()");
		aFilterChain.doFilter(aRequest, aResponse);
	}

	public void destroy()
	{

	}

	ServletContext servletContext;
}

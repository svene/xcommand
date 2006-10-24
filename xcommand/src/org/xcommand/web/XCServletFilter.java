package org.xcommand.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class XCServletFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException
	{
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException
	{
		Map ctx = new HashMap();
		XCRequestAttributeCV.setXcContext((HttpServletRequest) servletRequest, ctx);
		WebContextView.setRequest(ctx, (HttpServletRequest) servletRequest);
		WebContextView.setResponse(ctx, (HttpServletResponse) servletResponse);
		WebContextView.setServletContext(ctx, servletContext);
		System.out.println("XCServletFilter.doFilter()");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{

	}

	ServletContext servletContext;
}

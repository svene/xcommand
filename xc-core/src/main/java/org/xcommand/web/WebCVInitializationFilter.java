package org.xcommand.web;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.core.IDynaBeanProvider;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter responsible for initializing the XC Context with webapp-related information (request, response, servletContext)
 */
public class WebCVInitializationFilter implements Filter
{
	@Override
	public void init(FilterConfig filterConfig) {
		servletContext = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aFilterChain)
		throws IOException, ServletException
	{
		// Populate XC Context with webapp-related information (request, response):
		TCP.pushContext(TCP.newInheritableContext());
		webCV.setRequest((HttpServletRequest) aRequest);
		webCV.setResponse((HttpServletResponse) aResponse);
		System.out.println("WebCVInitializationFilter.doFilter()");
		aFilterChain.doFilter(aRequest, aResponse);
		TCP.popContext();
	}

	@Override
	public void destroy()
	{

	}

	ServletContext servletContext;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}

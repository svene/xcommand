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
	public void init(FilterConfig filterConfig) throws ServletException
	{
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aFilterChain)
		throws IOException, ServletException
	{
		// Populate XC Context with webapp-related information (request, response):
		TCP.pushNewInheritableContext();
		webCV.setRequest((HttpServletRequest) aRequest);
		webCV.setResponse((HttpServletResponse) aResponse);
		System.out.println("WebCVInitializationFilter.doFilter()");
		aFilterChain.doFilter(aRequest, aResponse);
		TCP.popContext();
	}

	public void destroy()
	{

	}

	ServletContext servletContext;
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
}

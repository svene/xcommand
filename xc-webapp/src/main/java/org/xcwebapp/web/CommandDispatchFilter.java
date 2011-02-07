package org.xcwebapp.web;

import org.xcommand.core.ICommand;
import org.xcwebapp.app.XcWebappCVProvider;

import javax.servlet.*;
import java.io.IOException;

public class CommandDispatchFilter implements Filter
{
	public void init(FilterConfig aFilterConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest aServletRequest, ServletResponse aServletResponse, FilterChain aFilterChain)
		throws IOException, ServletException
	{
		System.out.println("XCommandDispatchFilter.doFilter()");
		System.out.flush();

		ICommand cmd = cvp.getAppCV().getAppBeanProvider().getRequestDispatcherCommand();
		if (cmd != null)
		{
			cmd.execute();
		}
//		else
//
//		IXCommand xcmd = AppCV.getAppBeanProvider(TCP.getContext()).getRequestDispatcherXCommand();
//		xcmd.execute(TCP.getContext());
		// Leave this commented out (outherwise it does not work (at least with Jetty)):
//		aFilterChain.doFilter(aServletRequest, aServletResponse);
	}

	public void destroy()
	{
	}
	private XcWebappCVProvider cvp = new XcWebappCVProvider();
}
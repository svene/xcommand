package org.xcommand.web.jettyintegration;

import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Response;
import org.xcommand.web.WebContextView;
import org.xcommand.core.IXCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class StaticXCJettyHandler extends AbstractHandler
{
	public void handle(String aString, HttpServletRequest aHttpServletRequest,
		HttpServletResponse aHttpServletResponse, int i) throws IOException, javax.servlet.ServletException
	{
		Request base_request = (aHttpServletRequest instanceof Request) ?
			(Request) aHttpServletRequest : HttpConnection.getCurrentConnection().getRequest();
		Response base_response = (aHttpServletResponse instanceof Response) ?
			(Response)aHttpServletResponse : HttpConnection.getCurrentConnection().getResponse();
		base_request.setHandled(true);

		Map ctx = new HashMap();
		JettyCV.setJettyRequest(ctx, base_request);
		JettyCV.setJettyResponse(ctx, base_response);
		WebContextView.setRequest(ctx, aHttpServletRequest);
		WebContextView.setResponse(ctx, aHttpServletResponse);

		cmd.execute(ctx);
	}

	static
	{
		// Determine default commandlet:
		String classname;
		classname = System.getProperty("xc.dcc");
		if (classname == null)
		{
			classname = System.getProperty("xc.default-command-classname");
		}
		if (classname == null)
		{
			classname = "";
			System.err.println("XC-default-commandlet-classname neither defined by System-property " + "" +
				"'xc.default-command-classname' nor 'xc.dcc'. ");
			throw new RuntimeException("XC-default-commandlet-classname not defined");
		}
		try
		{
			Class clazz = Class.forName(classname);
			if (IXCommand.class.isAssignableFrom(clazz))
			{
				cmd = (IXCommand) clazz.newInstance();
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static IXCommand cmd;
}

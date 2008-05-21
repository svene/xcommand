package org.xcommand.integration.jetty;

import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;
import org.mortbay.jetty.handler.AbstractHandler;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.web.IWebCV;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class XCJettyHandler extends AbstractHandler
{

// --- Setting ---

	public void setXcommand(ICommand aCommand)
	{
		xCommand = aCommand;
	}

// --- Processing ---

	public void handle(String aString, HttpServletRequest aHttpServletRequest,
		HttpServletResponse aHttpServletResponse, int i) throws IOException, javax.servlet.ServletException
	{
		Request base_request = (aHttpServletRequest instanceof Request) ?
			(Request) aHttpServletRequest : HttpConnection.getCurrentConnection().getRequest();
		Response base_response = (aHttpServletResponse instanceof Response) ?
			(Response)aHttpServletResponse : HttpConnection.getCurrentConnection().getResponse();
		base_request.setHandled(true);

		TCP.pushContext(new HashMap());
		jettyCV.setJettyRequest(base_request);
		jettyCV.setJettyResponse(base_response);
		webCV.setRequest(aHttpServletRequest);
		webCV.setResponse(aHttpServletResponse);

		xCommand.execute();
		TCP.popContext();
	}


// --- Implementation ---

	private ICommand xCommand;
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IWebCV webCV = (IWebCV) dbp.getBeanForInterface(IWebCV.class);
	private IJettyCV jettyCV = (IJettyCV) dbp.getBeanForInterface(IJettyCV.class);
}

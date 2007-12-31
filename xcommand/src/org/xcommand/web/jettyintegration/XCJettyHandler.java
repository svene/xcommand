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

public class XCJettyHandler extends AbstractHandler
{

// --- Setting ---

	public void setXcommand(IXCommand aXCommand)
	{
		xCommand = aXCommand;
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

		Map ctx = new HashMap();
		JettyCV.setJettyRequest(ctx, base_request);
		JettyCV.setJettyResponse(ctx, base_response);
		WebContextView.setRequest(ctx, aHttpServletRequest);
		WebContextView.setResponse(ctx, aHttpServletResponse);

		xCommand.execute(ctx);
	}


// --- Implementation ---

	private IXCommand xCommand;
}

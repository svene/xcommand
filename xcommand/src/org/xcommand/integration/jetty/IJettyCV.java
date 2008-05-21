package org.xcommand.integration.jetty;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;

public interface IJettyCV
{
	public Request getJettyRequest();
	public Response getJettyResponse();
	public void setJettyRequest(Request aJettyRequest);
	public void setJettyResponse(Response aJettyResponse);
}

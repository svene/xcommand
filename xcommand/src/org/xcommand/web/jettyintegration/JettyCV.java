package org.xcommand.web.jettyintegration;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;

import java.util.Map;

public class JettyCV
{

// --- Access ---

	public static Request getJettyRequest(Map aCtx)
	{
		return (Request) aCtx.get(KEY_JETTY_REQUEST);
	}
	public static Response getJettyResponse(Map aCtx)
	{
		return (Response) aCtx.get(KEY_JETTY_RESPONSE);
	}

// --- Setting ---

	public static void setJettyRequest(Map aCtx, Request aJettyRequest)
	{
		aCtx.put(KEY_JETTY_REQUEST, aJettyRequest);
	}
	public static void setJettyResponse(Map aCtx, Response aJettyResponse)
	{
		aCtx.put(KEY_JETTY_RESPONSE, aJettyResponse);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.web.jettyintegration.JettyCV.";
	private static final String KEY_JETTY_REQUEST = NS + "JETTY_REQUEST";
	private static final String KEY_JETTY_RESPONSE = NS + "JETTY_RESPONSE";
}

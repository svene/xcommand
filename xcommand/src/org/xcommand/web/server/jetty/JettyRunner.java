package org.xcommand.web.server.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.nio.SelectChannelConnector;

import java.util.Map;

public class JettyRunner
{

// --- Access ---

	public int getPort()
	{
		return port;
	}

	public String getBase()
	{
		return base;
	}

	public String getContext()
	{
		return context;
	}

// --- Setting ---

	public void setPort(int aPort)
	{
		port = aPort;
	}

	public void setBase(String aBase)
	{
		base = aBase;
	}

	public void setContext(String aContext)
	{
		context = aContext;
	}

// --- Processing ---

	public void execute(Map aCtx) throws Exception
	{
		System.out.println("using port: '" + port + "'");
		System.out.println("using base: '" + base + "'");
		System.out.println("using context: '" + context + "'");

		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		server.setConnectors(new Connector[]{connector});
		WebAppContext wah = new WebAppContext();
		wah.setResourceBase(base);
		wah.setContextPath(context);
//		wah.setWar("file:///tmp/myspecialapp.war");
		server.addHandler(wah);
		server.start();
	}



// --- Implementation ---

	private int port = 8080;
	private String base = "."; // filesystem rootdir for HTML documents
	private String context = "/"; // context String for webapp

}

package org.xcommand.web.server.jetty;
/* Jetty 6
import org.mortbay.jetty.Server;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.nio.SelectChannelConnector;

import java.util.Map;
import java.util.HashMap;
*/
public class MiniJetty
{
	public static void main(String[] args) throws Exception
	{
/* Jetty 6
		try
		{
			MiniJetty m = new MiniJetty();
			Map ctx = new HashMap();
			for (int i = 0, n = args.length; i < n; i++)
			{
				String s = args[i];
				int p = s.indexOf('=');
				if (p == -1) continue;
				String k = s.substring(0, p);
				String v = s.substring(p + 1, s.length());
				System.out.println("argument: " + k + "=" + v);
				System.out.flush();
				ctx.put(k, v);
			}
			m.execute(ctx);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void execute(Map aCtx) throws Exception
	{
		// port:
		int port = 8080;
		String s = getConfigString(aCtx, "port", "8080");
		port = Integer.parseInt(s);
		System.out.println("using port: '" + port + "'");
		String base = getConfigString(aCtx, "base", ".");
		System.out.println("using base: '" + base + "'");
		String context = getConfigString(aCtx, "context", "/context");
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

	private String getConfigString(Map aCtx, String aName, String aDefault)
	{
		String s = (String) aCtx.get(aName);
		return (s != null) ? s : aDefault;
*/
	}
}

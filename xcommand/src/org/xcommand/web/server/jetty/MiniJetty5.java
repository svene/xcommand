package org.xcommand.web.server.jetty;

// Jetty 5 import org.mortbay.jetty.Server;
// Jetty 5import org.mortbay.http.HttpListener;
// Jetty 5import org.mortbay.http.SocketListener;
import org.xcommand.util.MainRoutines;

import java.util.Map;
import java.util.HashMap;

public class MiniJetty5
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			MiniJetty5 m = new MiniJetty5();
			Map ctx = MainRoutines.newNameValueMapFromArgs(args);
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
		String s = MainRoutines.getConfigString(aCtx, "port", "8080");
		port = Integer.parseInt(s);
		System.out.println("using port: '" + port + "'");
		String base = MainRoutines.getConfigString(aCtx, "base", ".");
		System.out.println("using base: '" + base + "'");
		String context = MainRoutines.getConfigString(aCtx, "context", "/context");
		System.out.println("using context: '" + context + "'");

/* Jetty 5 Implementation (commented out due to the migration to Jetty 6

		HttpListener listener = new SocketListener();
		listener.setPort(port);
		Server server = new Server();
		server.addListener(listener);
		server.addWebApplication(context, base);
		server.start();
*/
	}

}

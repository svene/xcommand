package org.xcommand.web.server.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.http.HttpListener;
import org.mortbay.http.SocketListener;

import java.util.Map;
import java.util.HashMap;

public class MiniJetty5
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			MiniJetty5 m = new MiniJetty5();
			Map ctx = newNameValueMapFromArgs(args);
			m.execute(ctx);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static Map newNameValueMapFromArgs(String[] aArgs)
	{
		Map result = new HashMap();
		for (int i = 0, n = aArgs.length; i < n; i++)
		{
			String s = aArgs[i];
			int p = s.indexOf('=');
			if (p == -1) continue;
			String k = s.substring(0, p);
			String v = s.substring(p + 1, s.length());
			System.out.println("argument: " + k + "=" + v);
			System.out.flush();
			result.put(k, v);
		}
		return result;
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

		HttpListener listener = new SocketListener();
		listener.setPort(port);
		Server server = new Server();
		server.addListener(listener);
		server.addWebApplication(context, base);
		server.start();
	}

	private String getConfigString(Map aCtx, String aName, String aDefault)
	{
		String s = (String) aCtx.get(aName);
		return (s != null) ? s : aDefault;
	}
}

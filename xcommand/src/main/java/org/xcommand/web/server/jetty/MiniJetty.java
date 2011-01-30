package org.xcommand.web.server.jetty;

import org.xcommand.util.MainRoutines;

import java.util.Map;

/**
 * Embedded Jetty6 server
 */
public class MiniJetty
{
	public static void main(String[] args) throws Exception
	{
/* Jetty 6*/
		try
		{
			MiniJetty m = new MiniJetty();
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
		JettyRunner jr = new JettyRunner();
		// port:
		String s = MainRoutines.getConfigString(aCtx, "port", "8080");
		int port = Integer.parseInt(s);
		jr.setPort(port);
		String base = MainRoutines.getConfigString(aCtx, "base", ".");
		jr.setBase(base);
		String context = MainRoutines.getConfigString(aCtx, "context", "/");
		jr.setContext(context);
		jr.execute(null);
	}

}

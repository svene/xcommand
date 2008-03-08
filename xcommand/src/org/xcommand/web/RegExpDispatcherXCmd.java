package org.xcommand.web;

import org.xcommand.core.IXCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegExpDispatcherXCmd implements IXCommand
{

	public void setCommands(Map aCommands)
	{
		commands = aCommands;
	}

	public void execute(Map aCtx)
	{
		HttpServletRequest request = WebXCV.getRequest(aCtx);
//		String path = request.getPathInfo();
		String path = request.getRequestURI();
		Iterator it = commands.keySet().iterator();
		boolean found = false;
		IXCommand cmd = null;
		while (!found && it.hasNext())
		{
			String ps = (String) it.next();
			Pattern p = Pattern.compile(ps);
			Matcher m = p.matcher(path);
			if (m.matches())
			{
				cmd = (IXCommand) commands.get(ps);
				found = true;
			}
		}

		if (cmd != null)
		{
			cmd.execute(aCtx);
		}

	}

	// Commands identified by pattern (e.g. '^/bla.*') used as key for this map:
	private Map commands;

}

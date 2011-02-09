package org.xcommand.web;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpDispatcherCmd implements ICommand
{

	public void setCommands(Map aCommands)
	{
		commands = aCommands;
	}

	public void execute()
	{
		HttpServletRequest request = webCV.getRequest();
		String path = request.getRequestURI();
		Iterator it = commands.keySet().iterator();
		boolean found = false;
		ICommand cmd = null;
		while (!found && it.hasNext())
		{
			String ps = (String) it.next();
			Pattern p = Pattern.compile(ps);
			Matcher m = p.matcher(path);
			if (m.matches())
			{
				cmd = (ICommand) commands.get(ps);
				found = true;
			}
		}

		if (cmd != null)
		{
			cmd.execute();
		}

	}

	// Commands identified by pattern (e.g. '^/bla.*') used as key for this map:
	private Map/*<ICommand>*/ commands;
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
}
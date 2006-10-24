package org.xcommand.core;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Command executing commands stored in a list
 */
public class ListCommand implements IXCommand
{

// --- Access ---

	public List getCommands()
	{
		return commands;
	}

// --- Setting ---

	public void setCommands(List aCommands)
	{
		commands = aCommands;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		Iterator it = commands.iterator();
		while (it.hasNext())
		{
			IXCommand cmd = (IXCommand) it.next();
			cmd.execute(aCtx);
		}
	}

// --- Implementation ---

	private List/*<IXCommand>*/ commands = new ArrayList();
}

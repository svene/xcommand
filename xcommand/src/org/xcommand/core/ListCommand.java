package org.xcommand.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Command executing commands stored in a list
 */
public class ListCommand implements INXCommand
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

	public void execute()
	{
		Iterator it = commands.iterator();
		while (it.hasNext())
		{
			INXCommand cmd = (INXCommand) it.next();
			cmd.execute();
		}
	}

// --- Implementation ---

	private List/*<INXCommand>*/ commands = new ArrayList();
}

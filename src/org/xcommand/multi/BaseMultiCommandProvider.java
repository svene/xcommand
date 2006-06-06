package org.xcommand.multi;

import java.util.Map;
import java.util.HashMap;

import org.xcommand.ICommand;

public class BaseMultiCommandProvider implements IMultiCommandProvider
{

// --- Access ---

	public Map getCommandMap()
	{
		return commandMap;
	}

	public ICommand getCommand(String aName)
	{
		return (ICommand) commandMap.get(aName);
	}

// --- Implementation ---

	protected Map commandMap = new HashMap();
}

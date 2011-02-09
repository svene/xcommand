package org.xcommand.core.multi;

import org.xcommand.core.ICommand;

import java.util.HashMap;
import java.util.Map;

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

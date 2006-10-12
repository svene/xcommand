package org.xcommand.core.multi;

import java.util.Map;
import java.util.HashMap;

import org.xcommand.core.IXCommand;

public class BaseMultiCommandProvider implements IMultiCommandProvider
{

// --- Access ---

	public Map getCommandMap()
	{
		return commandMap;
	}

	public IXCommand getCommand(String aName)
	{
		return (IXCommand) commandMap.get(aName);
	}

// --- Implementation ---

	protected Map commandMap = new HashMap();
}
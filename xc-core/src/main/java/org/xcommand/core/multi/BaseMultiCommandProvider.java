package org.xcommand.core.multi;

import org.xcommand.core.ICommand;

import java.util.HashMap;
import java.util.Map;

public class BaseMultiCommandProvider implements IMultiCommandProvider
{

	@Override
	public Map<String, ICommand> getCommandMap()
	{
		return commandMap;
	}

	@Override
	public ICommand getCommand(String aName)
	{
		return commandMap.get(aName);
	}

	protected Map<String, ICommand> commandMap = new HashMap<>();
}

package org.xcommand.core.multi;

import org.xcommand.core.ICommand;

import java.util.Map;

public interface IMultiCommandProvider
{
	Map<String, ICommand> getCommandMap();
	ICommand getCommand(String aName);
}

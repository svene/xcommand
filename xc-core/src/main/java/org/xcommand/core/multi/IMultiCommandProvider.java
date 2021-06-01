package org.xcommand.core.multi;

import org.xcommand.core.ICommand;

import java.util.Map;

public interface IMultiCommandProvider
{
// --- Access ---

	Map getCommandMap();
	ICommand getCommand(String aName);
}

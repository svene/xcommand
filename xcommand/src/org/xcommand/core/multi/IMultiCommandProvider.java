package org.xcommand.core.multi;

import org.xcommand.core.ICommand;

import java.util.Map;

public interface IMultiCommandProvider
{
// --- Access ---

	public Map getCommandMap();
	public ICommand getCommand(String aName);
}

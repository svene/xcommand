package org.xcommand.multi;

import java.util.Map;

import org.xcommand.ICommand;

public interface IMultiCommandProvider
{
// --- Access ---

	public Map getCommandMap();
	public ICommand getCommand(String aName);
}

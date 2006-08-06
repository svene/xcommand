package org.xcommand.multi;

import java.util.Map;

import org.xcommand.IXCommand;

public interface IMultiCommandProvider
{
// --- Access ---

	public Map getCommandMap();
	public IXCommand getCommand(String aName);
}

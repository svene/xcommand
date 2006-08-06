package org.xcommand.core.multi;

import java.util.Map;

import org.xcommand.core.IXCommand;

public interface IMultiCommandProvider
{
// --- Access ---

	public Map getCommandMap();
	public IXCommand getCommand(String aName);
}

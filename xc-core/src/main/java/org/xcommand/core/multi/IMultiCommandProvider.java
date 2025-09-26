package org.xcommand.core.multi;

import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

import java.util.Map;

public interface IMultiCommandProvider
{
	Map<String, ICommand> getCommandMap();
	@Nullable
	ICommand getCommand(String aName);
}

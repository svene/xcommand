package org.xcommand.multi;

import java.util.Map;

//!!!import org.xcommand.example.incubator.flow.core.FlowContextView;
import org.xcommand.IXCommand;

public class MultiCommand implements IXCommand, IMultiCommandProvider
{

// --- Initialization ---

	public MultiCommand(IMultiCommandProvider aProvider)
	{
		commandMap = aProvider.getCommandMap();
	}

	public MultiCommand()
	{
	}

// --- Access ---

	public Map getCommandMap()
	{
		return commandMap;
	}

	public IXCommand getCommand(String aName)
	{
		return (IXCommand) commandMap.get(aName);
	}

// --- Processing ---

	public void execute(Map aContext)
	{
/*!!!
		String mode = FlowContextView.getFlowMode(aContext);
		IXCommand cmd = (IXCommand) commandMap.get(mode);
		if (cmd == null) return;
		cmd.execute(aContext);
*/
	}

// --- Implementation ---

	private Map commandMap;
}

package org.xcommand.multi;

import java.util.Map;

//!!!import org.xcommand.example.incubator.flow.core.FlowContextView;
import org.xcommand.ICommand;

public class MultiCommand implements ICommand, IMultiCommandProvider
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

	public ICommand getCommand(String aName)
	{
		return (ICommand) commandMap.get(aName);
	}

// --- Processing ---

	public void execute(Map aContext)
	{
/*!!!
		String mode = FlowContextView.getFlowMode(aContext);
		ICommand cmd = (ICommand) commandMap.get(mode);
		if (cmd == null) return;
		cmd.execute(aContext);
*/
	}

// --- Implementation ---

	private Map commandMap;
}

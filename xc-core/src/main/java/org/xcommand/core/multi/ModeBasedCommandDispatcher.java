package org.xcommand.core.multi;

import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

import java.util.HashMap;
import java.util.Map;

public class ModeBasedCommandDispatcher implements ICommand
{

// --- Initialization ---

	public ModeBasedCommandDispatcher()
	{
	}

	public ModeBasedCommandDispatcher(String aModeKey)
	{
		modeKey = aModeKey;
	}

// --- Access ---

	public Map<String, ICommand> getModeCommandMap()
	{
		return modeCommandMap;
	}

// --- Setting ---

	public void setModeCommandMap(Map<String, ICommand> aModeCommandMap)
	{
		modeCommandMap = aModeCommandMap;
	}

// --- Processing ---

	@Override
	public void execute()
	{
		try
		{
//			String mode = ModeContextView.getMode(aCtx);
			String mode = (String) TCP.getContext().get(modeKey);
			ICommand command = (ICommand) getModeCommandMap().get(mode);
			command.execute();
		}
		catch (Exception e)
		{
			System.out.println("mode: " + ModeContextView.getMode());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

// --- Implementation ---

	private Map<String, ICommand> modeCommandMap = new HashMap<>();
	private String modeKey = ModeContextView.KEY_MODE;
}

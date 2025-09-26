package org.xcommand.core.multi;

import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

import java.util.HashMap;
import java.util.Map;

public class ModeBasedCommandDispatcher implements ICommand
{

	public ModeBasedCommandDispatcher()
	{
	}

	public ModeBasedCommandDispatcher(String aModeKey)
	{
		modeKey = aModeKey;
	}

	public Map<String, ICommand> getModeCommandMap()
	{
		return modeCommandMap;
	}

	public void setModeCommandMap(Map<String, ICommand> aModeCommandMap)
	{
		modeCommandMap = aModeCommandMap;
	}

	@Override
	public void execute()
	{
		try
		{
//			String mode = ModeContextView.getMode(aCtx);
			String mode = (String) TCP.getContext().get(modeKey);
			ICommand command = getModeCommandMap().get(mode);
			if (command != null) {
				command.execute();
			}
		}
		catch (RuntimeException e)
		{
			System.out.println("mode: " + ModeContextView.getMode());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Map<String, ICommand> modeCommandMap = new HashMap<>();
	private String modeKey = ModeContextView.KEY_MODE;
}

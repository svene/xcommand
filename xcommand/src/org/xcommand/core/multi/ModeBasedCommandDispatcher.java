package org.xcommand.core.multi;

import org.xcommand.core.IXCommand;

import java.util.Map;
import java.util.HashMap;

public class ModeBasedCommandDispatcher implements IXCommand
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

	public Map getModeCommandMap()
	{
		return modeCommandMap;
	}

// --- Setting ---

	public void setModeCommandMap(Map aModeCommandMap)
	{
		modeCommandMap = aModeCommandMap;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		try
		{
//			String mode = ModeContextView.getMode(aCtx);
			String mode = (String) aCtx.get(modeKey);
			IXCommand command = (IXCommand) getModeCommandMap().get(mode);
			command.execute(aCtx);
		}
		catch (Exception e)
		{
			System.out.println("mode: " + ModeContextView.getMode(aCtx));
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

// --- Implementation ---

	private Map modeCommandMap = new HashMap();
	private String modeKey = ModeContextView.KEY_MODE;
}

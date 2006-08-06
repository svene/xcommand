package org.xcommand.core.multi;

import org.xcommand.core.IXCommand;

import java.util.Map;
import java.util.HashMap;

public class ModeBasedCommandDispatcher implements IXCommand
{

// --- Access ---

	public Map getModeCommandMap()
	{
		return modeCommandMap;
	}

// --- Setting ---

	public void setModeCommandMap(Map aHandlerMap)
	{
		modeCommandMap = aHandlerMap;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		try
		{
			String mode = ModeContextView.getMode(aCtx);
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
}

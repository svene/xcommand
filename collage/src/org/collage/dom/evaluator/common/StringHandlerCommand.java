package org.collage.dom.evaluator.common;

import org.xcommand.core.IXCommand;

import java.util.Map;

/**
 * IXCommand wrapping an IStringHandler.
 * It reads the string from `StringHandlerCV.getString(aCtx)' for the `IStringHandler' 
 */
public class StringHandlerCommand implements IXCommand
{
	public StringHandlerCommand(IStringHandler aStringHandler)
	{
		stringHandler = aStringHandler;
	}

	public void execute(Map aCtx)
	{
		String s = StringHandlerCV.getString(aCtx);
		stringHandler.handleString(aCtx, s);
	}

	private IStringHandler stringHandler;
}

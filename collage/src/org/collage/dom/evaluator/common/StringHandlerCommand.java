package org.collage.dom.evaluator.common;

import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;

/**
 * ICommand wrapping an IStringHandler.
 * It reads the string from `StringHandlerCV.getString(aCtx)' for the `IStringHandler' 
 */
public class StringHandlerCommand implements ICommand
{
	public StringHandlerCommand(IStringHandler aStringHandler)
	{
		stringHandler = aStringHandler;
	}

	public void execute()
	{
		String s = stringHandlerCV.getString();
		stringHandler.handleString(TCP.getContext(), s);
	}

	private IStringHandler stringHandler;
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
}

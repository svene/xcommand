package org.collage.dom.evaluator.common;

import org.xcommand.core.*;

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

	@Override
	public void execute()
	{
		String s = stringHandlerCV.getString();
		stringHandler.handleString(TCP.getContext(), s);
	}

	private final IStringHandler stringHandler;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}

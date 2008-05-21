package org.collage.dom.evaluator.common;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

public abstract class StringHandlingHandler implements ICommand
{

// --- Initialization ---

	public StringHandlingHandler(StringHandlerCommand aStringHandlerCommand)
	{
		stringHandlerCommand = aStringHandlerCommand;
	}

// --- Processing ---

	public void execute()
	{
		String originalText = getOriginalText();
		String s = decoratedString(originalText);
		stringHandlerCV.setString(s);

		ICommand shc = stringHandlerCV.getStringHandlerCommand();
		if (shc == null) shc = stringHandlerCommand;
		shc.execute();
	}

// --- Implementation ---

	protected abstract String getOriginalText();

	protected String decoratedString(String aString)
	{
		return aString;
	}

	private StringHandlerCommand stringHandlerCommand;
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
}

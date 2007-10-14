package org.collage.dom.evaluator.common;

import org.xcommand.core.IXCommand;

import java.util.Map;

public abstract class StringHandlingHandler implements IXCommand
{

// --- Initialization ---

	public StringHandlingHandler(StringHandlerCommand aStringHandlerCommand)
	{
		stringHandlerCommand = aStringHandlerCommand;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		String originalText = getOriginalText(aCtx);
		String s = decoratedString(aCtx, originalText);
		StringHandlerCV.setString(aCtx, s);

		IXCommand shc = StringHandlerCV.getStringHandlerCommand(aCtx);
		if (shc == null) shc = stringHandlerCommand;
		shc.execute(aCtx);
	}

// --- Implementation ---

	protected abstract String getOriginalText(Map aCtx);

	protected String decoratedString(Map aCtx, String aString)
	{
		return aString;
	}

	private StringHandlerCommand stringHandlerCommand;
}

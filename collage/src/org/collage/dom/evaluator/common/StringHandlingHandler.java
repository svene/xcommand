package org.collage.dom.evaluator.common;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

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
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}

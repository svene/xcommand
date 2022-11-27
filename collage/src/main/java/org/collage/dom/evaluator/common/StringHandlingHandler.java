package org.collage.dom.evaluator.common;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public abstract class StringHandlingHandler implements ICommand {

	public StringHandlingHandler(StringHandlerCommand aStringHandlerCommand) {
		stringHandlerCommand = aStringHandlerCommand;
	}

	@Override
	public void execute() {
		var originalText = getOriginalText();
		var s = decoratedString(originalText);
		stringHandlerCV.setString(s);

		var shc = stringHandlerCV.getStringHandlerCommand();
		if (shc == null) {
			shc = stringHandlerCommand;
		}
		shc.execute();
	}

	protected abstract String getOriginalText();

	protected String decoratedString(String aString) {
		return aString;
	}

	private final StringHandlerCommand stringHandlerCommand;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}

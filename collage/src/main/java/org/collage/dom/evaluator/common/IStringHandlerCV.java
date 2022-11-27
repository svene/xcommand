package org.collage.dom.evaluator.common;

import org.xcommand.core.ICommand;

public interface IStringHandlerCV {
	public ICommand getStringHandlerCommand();

	public String getString();

	public void setStringHandlerCommand(ICommand aStringHandler);

	public void setString(String aString);
}

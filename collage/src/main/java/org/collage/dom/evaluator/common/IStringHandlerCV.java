package org.collage.dom.evaluator.common;

import org.xcommand.core.ICommand;

public interface IStringHandlerCV {
    ICommand getStringHandlerCommand();

    String getString();

    void setStringHandlerCommand(ICommand aStringHandler);

    void setString(String aString);

    boolean hasString();
}

package org.collage.dom.evaluator.common;

import java.util.Optional;
import org.xcommand.core.ICommand;

public interface IStringHandlerCV {
    ICommand getStringHandlerCommand();

    Optional<String> getString();

    void setStringHandlerCommand(ICommand aStringHandler);

    void setString(String aString);
}

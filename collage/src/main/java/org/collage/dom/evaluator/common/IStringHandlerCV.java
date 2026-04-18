package org.collage.dom.evaluator.common;

import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

public interface IStringHandlerCV {
    ICommand getStringHandlerCommand();

    String getString();

    void setStringHandlerCommand(ICommand aStringHandler);

    void setString(String aString);

    String NS = IStringHandlerCV.class.getName() + ".";
    String KEY_STRING = NS + "String";

    static boolean hasString() {
        return TCP.getContext().containsKey(KEY_STRING);
    }
}

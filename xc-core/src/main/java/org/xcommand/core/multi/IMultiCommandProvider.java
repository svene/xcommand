package org.xcommand.core.multi;

import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

public interface IMultiCommandProvider {
    Map<String, ICommand> getCommandMap();

    @Nullable
    ICommand getCommand(String aName);
}

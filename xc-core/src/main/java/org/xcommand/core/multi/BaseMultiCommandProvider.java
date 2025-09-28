package org.xcommand.core.multi;

import java.util.HashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

public class BaseMultiCommandProvider implements IMultiCommandProvider {

    @Override
    public Map<String, ICommand> getCommandMap() {
        return commandMap;
    }

    @Override
    @Nullable
    public ICommand getCommand(String aName) {
        return commandMap.get(aName);
    }

    protected Map<String, ICommand> commandMap = new HashMap<>();
}

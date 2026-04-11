package org.xcommand.core.multi;

import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

public sealed class BaseMultiCommandProvider implements IMultiCommandProvider
        permits ClassBasedMultiCommandProvider, ObjectBasedMultiCommandProvider {

    protected BaseMultiCommandProvider(Map<String, ICommand> commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    public Map<String, ICommand> getCommandMap() {
        return commandMap;
    }

    @Override
    @Nullable
    public ICommand getCommand(String aName) {
        return commandMap.get(aName);
    }

    private final Map<String, ICommand> commandMap;
}

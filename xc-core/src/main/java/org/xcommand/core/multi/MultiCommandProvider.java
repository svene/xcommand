package org.xcommand.core.multi;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

public final class MultiCommandProvider implements IMultiCommandProvider {

    public static MultiCommandProvider fromClass(Class<?> aClazz) {
        return new MultiCommandProvider(Arrays.stream(aClazz.getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .collect(Collectors.toMap(m -> m.getName(), m -> MethodCmd.fromClass(aClazz, m))));
    }

    public static MultiCommandProvider fromObject(Object aTargetObject) {
        return new MultiCommandProvider(Arrays.stream(aTargetObject.getClass().getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .collect(Collectors.toMap(m -> m.getName(), m -> MethodCmd.fromObject(aTargetObject, m))));
    }

    private MultiCommandProvider(Map<String, ICommand> commandMap) {
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

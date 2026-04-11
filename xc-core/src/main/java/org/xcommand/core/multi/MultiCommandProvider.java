package org.xcommand.core.multi;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

public final class MultiCommandProvider implements IMultiCommandProvider {

    public static MultiCommandProvider fromClass(Class<?> aClazz) {
        return new MultiCommandProvider(
                commandMethods(aClazz).collect(Collectors.toMap(Method::getName, m -> MethodCmd.fromClass(aClazz, m))));
    }

    public static MultiCommandProvider fromObject(Object aTargetObject) {
        return new MultiCommandProvider(commandMethods(aTargetObject.getClass())
                .collect(Collectors.toMap(Method::getName, m -> MethodCmd.fromObject(aTargetObject, m))));
    }

    private static Stream<Method> commandMethods(Class<?> aClazz) {
        return Arrays.stream(aClazz.getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class));
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

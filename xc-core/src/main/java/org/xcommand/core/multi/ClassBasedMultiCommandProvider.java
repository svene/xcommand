package org.xcommand.core.multi;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.xcommand.core.ICommand;

public final class ClassBasedMultiCommandProvider extends BaseMultiCommandProvider {
    public ClassBasedMultiCommandProvider(Class<?> aClazz) {
        super(buildCommandMap(aClazz));
    }

    private static Map<String, ICommand> buildCommandMap(Class<?> aClazz) {
        return Arrays.stream(aClazz.getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .collect(Collectors.toMap(m -> m.getName(), m -> MethodCmd.fromClass(aClazz, m)));
    }
}

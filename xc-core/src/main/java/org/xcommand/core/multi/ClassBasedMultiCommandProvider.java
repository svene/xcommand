package org.xcommand.core.multi;

import java.util.Arrays;
import java.util.Map;

public final class ClassBasedMultiCommandProvider extends BaseMultiCommandProvider {
    public ClassBasedMultiCommandProvider(Class<?> aClazz) {
        Arrays.stream(aClazz.getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .forEach(m -> commandMap.put(m.getName(), MethodCmd.fromClass(aClazz, m)));
    }
}

package org.xcommand.core.multi;

import java.util.Arrays;
import java.util.Map;

public final class ObjectBasedMultiCommandProvider extends BaseMultiCommandProvider {
    public ObjectBasedMultiCommandProvider(Object aTargetObject) {
        Arrays.stream(aTargetObject.getClass().getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .forEach(m -> commandMap.put(m.getName(), MethodCmd.fromObject(aTargetObject, m)));
    }
}

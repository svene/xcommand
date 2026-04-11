package org.xcommand.core.multi;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class ObjectBasedMultiCommandProvider extends BaseMultiCommandProvider {

    public ObjectBasedMultiCommandProvider(Object aTargetObject) {
        this.targetObject = aTargetObject;
    }

    public void init() {
        commandMap = new HashMap<>();
        Arrays.stream(targetObject.getClass().getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .forEach(m -> commandMap.put(m.getName(), MethodCmd.fromObject(targetObject, m)));
    }

    public void setTargetObject(Object aTargetObject) {
        targetObject = aTargetObject;
    }

    private Object targetObject;
}

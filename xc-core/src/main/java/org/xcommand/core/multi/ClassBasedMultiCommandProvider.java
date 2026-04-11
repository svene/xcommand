package org.xcommand.core.multi;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public final class ClassBasedMultiCommandProvider extends BaseMultiCommandProvider {
    public ClassBasedMultiCommandProvider(Class<?> aClazz) {
        this.targetClass = aClazz;
    }

    //	public ClassBasedMultiCommandProvider() {
    //	}

    public void init() {
        Arrays.stream(targetClass.getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .forEach(m -> commandMap.put(m.getName(), MethodCmd.fromClass(targetClass, m)));
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> aTargetClass) {
        targetClass = aTargetClass;
    }

    private Class<?> targetClass;
}

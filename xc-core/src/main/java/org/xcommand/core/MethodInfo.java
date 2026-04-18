package org.xcommand.core;

import java.lang.reflect.Method;

record MethodInfo(Method method, boolean isSetter, boolean isHas, String methodClassName, String property) {
    public String propertyPath() {
        return methodClassName + "." + property;
    }

    public static MethodInfo from(Method method) {
        boolean isSetter = method.getName().startsWith("set");
        boolean isHas = method.getName().startsWith("has");
        return new MethodInfo(
                method,
                isSetter,
                isHas,
                method.getDeclaringClass().getName(),
                method.getName().substring(3));
    }
}

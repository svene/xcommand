package org.xcommand.core;

import java.lang.reflect.Method;

record MethodInfo(Method method, boolean isSetter, String methodClassName, String property) {
    public String propertyPath() {
        return methodClassName + "." + property;
    }

    public static MethodInfo from(Method method) {
        return new MethodInfo(
                method,
                method.getName().startsWith("set"),
                method.getDeclaringClass().getName(),
                method.getName().substring(3));
    }
}

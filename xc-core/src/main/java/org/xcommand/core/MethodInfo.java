package org.xcommand.core;

import java.lang.reflect.Method;

sealed interface MethodInfo permits MethodInfo.Getter, MethodInfo.Setter {

    Method method();

    String methodClassName();

    String property();

    default String propertyPath() {
        return methodClassName() + "." + property();
    }

    record Getter(Method method, String methodClassName, String property) implements MethodInfo {}

    record Setter(Method method, String methodClassName, String property) implements MethodInfo {}

    static MethodInfo from(Method method) {
        String className = method.getDeclaringClass().getName();
        String property = method.getName().substring(3);
        return method.getName().startsWith("set")
                ? new Setter(method, className, property)
                : new Getter(method, className, property);
    }
}

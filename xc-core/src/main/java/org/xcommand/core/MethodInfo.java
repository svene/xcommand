package org.xcommand.core;

import java.lang.reflect.Method;

sealed interface MethodInfo permits MethodInfo.Getter, MethodInfo.Setter, MethodInfo.Has {

    Method method();

    String methodClassName();

    String property();

    default String propertyPath() {
        return methodClassName() + "." + property();
    }

    record Getter(Method method, String methodClassName, String property) implements MethodInfo {}

    record Setter(Method method, String methodClassName, String property) implements MethodInfo {}

    record Has(Method method, String methodClassName, String property) implements MethodInfo {}

    static MethodInfo from(Method method) {
        String className = method.getDeclaringClass().getName();
        String property = method.getName().substring(3);
        return switch (method.getName().substring(0, 3)) {
            case "set" -> new Setter(method, className, property);
            case "has" -> new Has(method, className, property);
            default -> new Getter(method, className, property);
        };
    }
}

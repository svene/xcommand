package org.xcommand.core;

public record InvocationContext(Object proxy, MethodInfo methodInfo, Object[] args) {
}

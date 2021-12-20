package org.xcommand.core;

public record InvocationHandlerContext(Object proxy, MethodInfo methodInfo, Object[] args) {
}

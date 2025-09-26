package org.xcommand.core;

import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class DynaBeanInvocationHandler implements InvocationHandler {
	public DynaBeanInvocationHandler(InvocationContextHandler invocationContextHandler) {
		this.invocationContextHandler = invocationContextHandler;
	}

	@Override
	@Nullable
	public Object invoke(Object proxy, Method method, Object[] args) {
		MethodInfo mi = methodInfoMap.computeIfAbsent(method, key -> MethodInfo.from(method));
		return invocationContextHandler.invoke(new InvocationContext(proxy, mi, args));
	}

	private final InvocationContextHandler invocationContextHandler;
	private final ConcurrentHashMap<Method, MethodInfo> methodInfoMap = new ConcurrentHashMap<>();
}

package org.xcommand.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class DynaBeanInvocationHandler implements InvocationHandler
{
	public DynaBeanInvocationHandler(IBeanAccessor aBeanAccessor)
	{
		beanAccessor = aBeanAccessor;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		MethodInfo mi = methodInfoMap.computeIfAbsent(method, (key) -> new MethodInfo(method));

		var ihc = new InvocationHandlerContext(proxy, mi, args);
		if (mi.isSetter)
		{
			beanAccessor.set(ihc);
			return null;
		} else {
			return beanAccessor.get(ihc);
		}
	}
	private final IBeanAccessor beanAccessor;
	private final ConcurrentHashMap<Method, MethodInfo> methodInfoMap = new ConcurrentHashMap<>();
}

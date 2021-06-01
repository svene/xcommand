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
	public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) {
		MethodInfo mi = methodInfoMap.computeIfAbsent(aMethod, (key) -> new MethodInfo(aMethod));
		if (mi.isSetter)
		{
			beanAccessor.set(aProxy, mi, aArgs);
			return null;
		} else {
			return beanAccessor.get(aProxy, mi, aArgs);
		}
	}
	private final IBeanAccessor beanAccessor;
	private final ConcurrentHashMap<Method, MethodInfo> methodInfoMap = new ConcurrentHashMap<>();
}

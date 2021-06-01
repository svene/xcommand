package org.xcommand.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;

public class DynaBeanInvocationHandler implements InvocationHandler
{
	public DynaBeanInvocationHandler(IBeanAccessor aBeanAccessor)
	{
		beanAccessor = aBeanAccessor;
	}

	@Override
	public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) {
		// Get method:
		MethodInfo mi;
		synchronized (methodInfoMap)
		{
			if (methodInfoMap.containsKey(aMethod)) {
				mi = (MethodInfo) methodInfoMap.get(aMethod);
			} else {
				mi = new MethodInfo(aMethod);
				methodInfoMap.put(aMethod, mi);
			}
		}
		// Set or get value:
		if (mi.isSetter)
		{
			beanAccessor.set(aProxy, mi, aArgs);
			return null;
		}
		return beanAccessor.get(aProxy, mi, aArgs);
	}
	private final IBeanAccessor beanAccessor;
	private final Map methodInfoMap = new HashMap();
}

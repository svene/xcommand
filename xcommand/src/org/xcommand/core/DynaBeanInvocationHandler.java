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

	public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) throws Throwable
	{
		// Get method:
		MethodInfo mi;
		synchronized (methodInfoMap)
		{
			if (!methodInfoMap.containsKey(aMethod))
			{
				mi = new MethodInfo(aMethod);
				methodInfoMap.put(aMethod, mi);
			}
			else
			{
				mi = (MethodInfo) methodInfoMap.get(aMethod);
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
	private IBeanAccessor beanAccessor;
	private final Map methodInfoMap = new HashMap();
}

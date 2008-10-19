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

	public Object invoke(Object aTargetObj, Method aMethod, Object[] aArgs) throws Throwable
	{
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
		if (mi.isSetter)
		{
			beanAccessor.set(aTargetObj, mi, aArgs);
			return null;
		}
		return beanAccessor.get(aTargetObj, mi, aArgs);
	}
	private IBeanAccessor beanAccessor;
	private final Map methodInfoMap = new HashMap();
}

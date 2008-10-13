package org.xcommand.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;

public class DynaBeanInvocationHandler implements InvocationHandler
{
	public DynaBeanInvocationHandler(boolean aUseIdentityForKey)
	{
		useIdentityForKey = aUseIdentityForKey;
		setContextProvider(new IContextProvider()
		{
			public Map getContext()
			{
				return TCP.getContext();
			}
		});
	}

	public Object invoke(Object aTargetObj, Method aMethod, Object[] aArgs) throws Throwable
	{
		MethodInfo mi;
		synchronized (methodInfoMap)
		{
			if (!methodInfoMap.containsKey(aMethod))
			{
				mi = new MethodInfo();
				mi.method = aMethod;
				String methodName = aMethod.getName();
				mi.isSetter = methodName.startsWith("set");
				Class clazz = aMethod.getDeclaringClass();
				mi.property = clazz.getName() + "." + methodName.substring(3);
				methodInfoMap.put(aMethod, mi);
			}
			else
			{
				mi = (MethodInfo) methodInfoMap.get(aMethod);
			}
		}
		String key = useIdentityForKey ? System.identityHashCode(aTargetObj) + mi.property : mi.property;
		//System.out.println("***key = " + key);
		if (mi.isSetter)
		{
			if (aArgs.length == 1)
			{
				contextProvider.getContext().put(key, aArgs[0]);
			}
			else
			{
				contextProvider.getContext().put(key, aArgs);
			}
		}
		else
		{
			return contextProvider.getContext().get(key);
		}
		return null;
	}
	public void setContextProvider(IContextProvider aContextProvider)
	{
		contextProvider = aContextProvider;
	}
	private IContextProvider contextProvider;
	private boolean useIdentityForKey;

	private final Map methodInfoMap = new HashMap();

	private class MethodInfo
	{
		Method method;
		boolean isSetter;
		String property;
	}
}

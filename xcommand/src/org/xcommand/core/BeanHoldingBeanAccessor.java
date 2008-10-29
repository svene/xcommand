package org.xcommand.core;

public class BeanHoldingBeanAccessor implements IBeanAccessor
{
	public BeanHoldingBeanAccessor(Object aObj)
	{
		obj = aObj;
	}

	public void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		try
		{
			aMethodInfo.method.invoke(obj, aArgs);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		try
		{
			return aMethodInfo.method.invoke(obj, aArgs);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	Object obj;
}

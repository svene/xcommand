package org.xcommand.core;

public class BeanHoldingBeanAccessor implements IBeanAccessor
{
	public BeanHoldingBeanAccessor(Object aObj)
	{
		obj = aObj;
	}

	@Override
	public void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		try
		{
			aMethodInfo.method.invoke(obj, aArgs);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		try
		{
			return aMethodInfo.method.invoke(obj, aArgs);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private final Object obj;
}

package org.xcommand.core;

import org.jooq.lambda.Sneaky;

public record BeanHoldingBeanAccessor(Object obj) implements IBeanAccessor
{
	@Override
	public void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		Sneaky.runnable(() -> aMethodInfo.method.invoke(obj, aArgs)).run();
	}

	@Override
	public Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		return Sneaky.supplier(() -> aMethodInfo.method.invoke(obj, aArgs)).get();
	}
}

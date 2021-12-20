package org.xcommand.core;

import org.jooq.lambda.Sneaky;

public record BeanHoldingBeanAccessor(Object obj) implements IBeanAccessor
{
	@Override
	public void set(InvocationHandlerContext ihc)
	{
		Sneaky.runnable(
			() -> ihc.methodInfo().method.invoke(obj, ihc.args())
		).run();
	}

	@Override
	public Object get(InvocationHandlerContext ihc)
	{
		return Sneaky.supplier(
			() -> ihc.methodInfo().method.invoke(obj, ihc.args())
		).get();
	}
}

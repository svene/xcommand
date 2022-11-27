package org.xcommand.core;

import org.jooq.lambda.Sneaky;

public record BeanHoldingBeanAccessor(Object obj) implements IBeanAccessor
{
	@Override
	public void set(InvocationContext ihc) {
		Sneaky.runnable(
			() -> ihc.methodInfo().method().invoke(obj, ihc.args())
		).run();
	}

	@Override
	public Object get(InvocationContext ihc) {
		return Sneaky.supplier(
			() -> ihc.methodInfo().method().invoke(obj, ihc.args())
		).get();
	}
}

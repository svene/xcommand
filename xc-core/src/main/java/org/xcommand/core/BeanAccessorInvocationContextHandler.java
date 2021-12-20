package org.xcommand.core;

/**
 * Adapter from invoke call to get/set call
 */
public record BeanAccessorInvocationContextHandler(IBeanAccessor beanAccessor) implements InvocationContextHandler {

	@Override
	public Object invoke(InvocationContext ihc) {
		if (ihc.methodInfo().isSetter)
		{
			beanAccessor.set(ihc);
			return null;
		} else {
			return beanAccessor.get(ihc);
		}
	}
}

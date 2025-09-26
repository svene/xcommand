package org.xcommand.core;

import org.jspecify.annotations.Nullable;

/**
 * Adapter from invoke call to get/set call
 */
public record BeanAccessorInvocationContextHandler(IBeanAccessor beanAccessor) implements InvocationContextHandler {

	@Override
	@Nullable
	public Object invoke(InvocationContext ihc) {
		if (ihc.methodInfo().isSetter()) {
			beanAccessor.set(ihc);
			return null;
		} else {
			return beanAccessor.get(ihc);
		}
	}
}

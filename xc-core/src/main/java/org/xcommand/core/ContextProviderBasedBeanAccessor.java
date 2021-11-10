package org.xcommand.core;

import java.util.Map;
import java.util.function.Supplier;

public record ContextProviderBasedBeanAccessor(
	Supplier<Map<String, Object>> contextSupplier,
	IDynaBeanKeyProvider dynaBeanKeyProvider
) implements IBeanAccessor {

	@Override
	public void set(Object targetObj, MethodInfo methodInfo, Object[] args) {
		String key = dynaBeanKeyProvider.getKey(targetObj, methodInfo, args);
		contextSupplier.get().put(key, args.length == 1 ? args[0] : args);
	}

	@Override
	public Object get(Object targetObj, MethodInfo methodInfo, Object[] args) {
		String key = dynaBeanKeyProvider.getKey(targetObj, methodInfo, args);
		return contextSupplier.get().get(key);
	}

}

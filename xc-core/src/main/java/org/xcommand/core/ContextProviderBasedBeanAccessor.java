package org.xcommand.core;

import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public record ContextProviderBasedBeanAccessor(
	Supplier<Map<String, Object>> contextSupplier,
	IDynaBeanKeyProvider dynaBeanKeyProvider
) implements IBeanAccessor {

	@Override
	public void set(InvocationContext ihc) {
		String key = dynaBeanKeyProvider.getKey(ihc);
		Object[] args = ihc.args();
		contextSupplier.get().put(key, args.length == 1 ? args[0] : args);
	}

	@Override
	@Nullable
	public Object get(InvocationContext ihc) {
		String key = dynaBeanKeyProvider.getKey(ihc);
		return contextSupplier.get().get(key);
	}

}

package org.xcommand.core;

public record ContextProviderBasedBeanAccessor(
	IContextProvider contextProvider,
	IDynaBeanKeyProvider dynaBeanKeyProvider
) implements IBeanAccessor {

	@Override
	public void set(Object targetObj, MethodInfo methodInfo, Object[] args) {
		String key = dynaBeanKeyProvider.getKey(targetObj, methodInfo, args);
		contextProvider.getContext().put(key, args.length == 1 ? args[0] : args);
	}

	@Override
	public Object get(Object targetObj, MethodInfo methodInfo, Object[] args) {
		String key = dynaBeanKeyProvider.getKey(targetObj, methodInfo, args);
		return contextProvider.getContext().get(key);
	}

}

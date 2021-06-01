package org.xcommand.core;

public class ContextProviderBasedBeanAccessor implements IBeanAccessor
{
	public ContextProviderBasedBeanAccessor(IContextProvider contextProvider, IDynaBeanKeyProvider dynaBeanKeyProvider)
	{
		this.contextProvider = contextProvider;
		this.dynaBeanKeyProvider = dynaBeanKeyProvider;
	}

	@Override
	public void set(Object targetObj, MethodInfo methodInfo, Object[] args)
	{
		String key = dynaBeanKeyProvider.getKey(targetObj, methodInfo, args);
		contextProvider.getContext().put(key, args.length == 1 ? args[0] : args);
	}

	@Override
	public Object get(Object targetObj, MethodInfo methodInfo, Object[] args)
	{
		String key = dynaBeanKeyProvider.getKey(targetObj, methodInfo, args);
		return contextProvider.getContext().get(key);
	}

	private final IContextProvider contextProvider;
	private final IDynaBeanKeyProvider dynaBeanKeyProvider;

}

package org.xcommand.core;

public class ContextProviderBasedBeanAccessor implements IBeanAccessor
{
	public ContextProviderBasedBeanAccessor(IContextProvider aContextProvider, IDynaBeanKeyProvider aDynaBeanKeyProvider)
	{
		contextProvider = aContextProvider;
		dynaBeanKeyProvider = aDynaBeanKeyProvider;
	}

	@Override
	public void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		String key = dynaBeanKeyProvider.getKey(aTargetObj, aMethodInfo, aArgs);
		contextProvider.getContext().put(key, aArgs.length == 1 ? aArgs[0] : aArgs);
	}

	@Override
	public Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		String key = dynaBeanKeyProvider.getKey(aTargetObj, aMethodInfo, aArgs);
		return contextProvider.getContext().get(key);
	}

	private final IContextProvider contextProvider;
	private final IDynaBeanKeyProvider dynaBeanKeyProvider;

}

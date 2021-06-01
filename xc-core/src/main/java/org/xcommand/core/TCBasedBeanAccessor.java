package org.xcommand.core;

/**
 * IBeanAccessor using ThreadLocal Context (TCP) as storage.
 */
public class TCBasedBeanAccessor implements IBeanAccessor
{
	public TCBasedBeanAccessor(IDynaBeanKeyProvider aDynaBeanKeyProvider)
	{
		contextProviderBasedBeanAccessor = new ContextProviderBasedBeanAccessor(TCP::getContext, aDynaBeanKeyProvider);
	}

	@Override
	public void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		contextProviderBasedBeanAccessor.set(aTargetObj, aMethodInfo, aArgs);
	}

	@Override
	public Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		return contextProviderBasedBeanAccessor.get(aTargetObj, aMethodInfo, aArgs);
	}

	private final ContextProviderBasedBeanAccessor contextProviderBasedBeanAccessor;
}

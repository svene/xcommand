package org.xcommand.core;

import java.util.Map;

/**
 * IBeanAccessor using ThreadLocal Context (TCP) as storage.
 */
public class TCBasedBeanAccessor implements IBeanAccessor
{
	public TCBasedBeanAccessor(IDynaBeanKeyProvider aDynaBeanKeyProvider)
	{
		contextProviderBasedBeanAccessor = new ContextProviderBasedBeanAccessor(cp, aDynaBeanKeyProvider);
	}

	public void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		contextProviderBasedBeanAccessor.set(aTargetObj, aMethodInfo, aArgs);
	}

	public Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		return contextProviderBasedBeanAccessor.get(aTargetObj, aMethodInfo, aArgs);
	}
	private IContextProvider cp = new IContextProvider()
	{
		public Map getContext()
		{
			return TCP.getContext();
		}
	};
	private ContextProviderBasedBeanAccessor contextProviderBasedBeanAccessor;
}

package org.xcommand.core;

import org.xcommand.proxy.Proxies;

public class DynaBeanProvider
{

	private DynaBeanProvider() {
	}

	public static IDynaBeanProvider newThreadClassMethodInstance() {
		return newThreadBasedDynabeanProvider(DynaBeanKeyProviders::classAndMethodKeyProvider);
	}

	static IDynaBeanProvider newThreadBasedDynabeanProvider(IDynaBeanKeyProvider aDynaBeanKeyProvider)
	{
		return newDynabeanProvider(BeanAccessors.newBeanAccessor(TCP::getContext, aDynaBeanKeyProvider));
	}

	static IDynaBeanProvider newDynabeanProvider(IBeanAccessor aBeanAccessor)
	{
		return new BasicDynaBeanProvider(new DynaBeanInvocationHandler(aBeanAccessor));
	}

// --- Implementation ---

	private static class BasicDynaBeanProvider implements IDynaBeanProvider
	{
		private BasicDynaBeanProvider(DynaBeanInvocationHandler dynaBeanInvocationHandler)
		{
			this.dynaBeanInvocationHandler = dynaBeanInvocationHandler;
		}

		@Override
		public <T> T newBeanForInterface(Class<T> aInterface)
		{
			return Proxies.newProxy(aInterface, dynaBeanInvocationHandler);
		}

		private final DynaBeanInvocationHandler dynaBeanInvocationHandler;
	}

}

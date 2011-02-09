package org.xcommand.core;

import java.lang.reflect.Proxy;

public class DynaBeanProvider
{
	public DynaBeanProvider()
	{
	}

	public static IDynaBeanProvider newThreadBasedDynabeanProvider(IDynaBeanKeyProvider aDynaBeanKeyProvider)
	{
		return newDynabeanProvider(new TCBasedBeanAccessor(aDynaBeanKeyProvider));
	}
	public static IDynaBeanProvider newDynabeanProvider(IBeanAccessor aBeanAccessor)
	{
		return new BasicDynaBeanProvider(new DynaBeanInvocationHandler(aBeanAccessor));
	}

// --- Implementation ---

	private static class BasicDynaBeanProvider implements IDynaBeanProvider
	{
		private BasicDynaBeanProvider(DynaBeanInvocationHandler aIh)
		{
			ih = aIh;
		}

		public Object newBeanForInterface(Class aInterface)
		{
			return newBeanFromInterfaces(new Class[]{aInterface});
		}

		public Object newBeanFromInterfaces(Class[] aInterfaces)
		{
			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), aInterfaces, ih);
		}

		private final DynaBeanInvocationHandler ih;
	}

}

package org.xcommand.core;

import java.lang.reflect.Proxy;
import java.util.Map;

public class DynaBeanProvider
{
	public DynaBeanProvider()
	{
	}

	public static IDynaBeanProvider getClassAndMethodBasedDynaBeanProvider()
	{
		return classAndMethodBasedDynaBeanProvider;
	}

	public static IDynaBeanProvider getObjectIdentityBasedDynaBeanProvider()
	{
		return objectIdentityBasedDynaBeanProvider;
	}

// --- Implementation ---

	private static final IDynaBeanProvider classAndMethodBasedDynaBeanProvider;
	private static final IDynaBeanProvider objectIdentityBasedDynaBeanProvider;
	static
	{
		IContextProvider cp = new IContextProvider()
		{
			public Map getContext()
			{
				return TCP.getContext();
			}
		};
		classAndMethodBasedDynaBeanProvider = new BasicDynaBeanProvider(new DynaBeanInvocationHandler(
			new ContextProviderBasedBeanAccessor(cp, new ClassAndMethodKeyProvider())));

		objectIdentityBasedDynaBeanProvider = new BasicDynaBeanProvider(new DynaBeanInvocationHandler(
			new ContextProviderBasedBeanAccessor(cp, new ObjectIdentityKeyProvider())));
	}

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

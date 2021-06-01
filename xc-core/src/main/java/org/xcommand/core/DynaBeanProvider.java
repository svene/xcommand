package org.xcommand.core;

import java.lang.reflect.Proxy;

public class DynaBeanProvider
{

	private DynaBeanProvider() {
	}

	public static IDynaBeanProvider newThreadClassMethodInstance() {
		return newDynabeanProvider(BeanAccessors.newThreadClassMethodInstance());
	}

	public static IDynaBeanProvider newThreadBasedDynabeanProvider(IDynaBeanKeyProvider aDynaBeanKeyProvider)
	{
		return newDynabeanProvider(BeanAccessors.newBeanAccessor(TCP::getContext, aDynaBeanKeyProvider));
	}

	public static IDynaBeanProvider newDynabeanProvider(IBeanAccessor aBeanAccessor)
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
			@SuppressWarnings("unchecked")
			T result = (T) newBeanFromInterfaces(new Class[]{aInterface});
			return result;
		}

		@Override
		public Object newBeanFromInterfaces(Class<?>[] aInterfaces)
		{
			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), aInterfaces, dynaBeanInvocationHandler);
		}

		private final DynaBeanInvocationHandler dynaBeanInvocationHandler;
	}

}

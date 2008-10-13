package org.xcommand.core;

public class DynaBeanProvider
{

	public Object getBeanForInterface(Class aInterface)
	{
		return ProxyFactory.newInstanceFromInterface(null, aInterface, dynaBeanInvocationHandler);
	}
	public Object newBeanForInterface(Class aInterface)
	{
		return ProxyFactory.newInstanceFromInterface(null, aInterface, multiDynaBeanInvocationHandler);
	}
	public Object newBeanFromInterfaces(Class[] aInterfaces)
	{
		return ProxyFactory.newInstanceFromInterfaces(null, aInterfaces, dynaBeanInvocationHandler);
	}

// --- Implementation ---

	private DynaBeanInvocationHandler dynaBeanInvocationHandler = new DynaBeanInvocationHandler(false);
	private DynaBeanInvocationHandler multiDynaBeanInvocationHandler = new DynaBeanInvocationHandler(true);
}

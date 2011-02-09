package org.xcommand.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory
{
	public static Object newInstanceFromInterface(ClassLoader aClassLoader, Class aInterface, InvocationHandler aIh)
	{
		return newInstanceFromInterfaces(aClassLoader, new Class[]{aInterface}, aIh);
	}
	public static Object newInstanceFromInterfaces(ClassLoader aClassLoader, Class[] aInterfaces, InvocationHandler aIh)
	{
		ClassLoader cl = aClassLoader;
		if (cl == null)
		{
			cl = Thread.currentThread().getContextClassLoader();
		}
		return Proxy.newProxyInstance(cl, aInterfaces, aIh);
	}
}

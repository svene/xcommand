package org.xcommand.core;

public interface IDynaBeanProvider
{
	public Object newBeanForInterface(Class aInterface);
	public Object newBeanFromInterfaces(Class[] aInterfaces);
}

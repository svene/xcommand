package org.xcommand.core;

public interface IDynaBeanProvider
{
	public <T> T newBeanForInterface(Class<T> aInterface);
	public Object newBeanFromInterfaces(Class<?>[] aInterfaces);
}

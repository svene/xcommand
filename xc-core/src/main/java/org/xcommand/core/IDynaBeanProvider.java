package org.xcommand.core;

public interface IDynaBeanProvider
{
	<T> T newBeanForInterface(Class<T> aInterface);
}

package org.xcommand.core;

public interface IBeanAccessor
{
	void set(InvocationHandlerContext ihc);
	Object get(InvocationHandlerContext ihc);
}

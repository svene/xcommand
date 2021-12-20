package org.xcommand.core;

public interface IBeanAccessor
{
	void set(InvocationContext ihc);
	Object get(InvocationContext ihc);
}

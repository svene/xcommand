package org.xcommand.core;

public interface IBeanAccessor
{
	void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs);
	Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs);
}

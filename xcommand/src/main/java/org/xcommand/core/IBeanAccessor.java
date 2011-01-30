package org.xcommand.core;

public interface IBeanAccessor
{
	public void set(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs);
	public Object get(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs);
}

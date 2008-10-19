package org.xcommand.core;

public interface IDynaBeanKeyProvider
{
	public String getKey(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs);
}

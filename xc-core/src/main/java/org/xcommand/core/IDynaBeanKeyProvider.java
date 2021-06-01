package org.xcommand.core;

public interface IDynaBeanKeyProvider
{
	String getKey(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs);
}

package org.xcommand.core;

@FunctionalInterface
public interface IDynaBeanKeyProvider
{
	String getKey(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs);
}

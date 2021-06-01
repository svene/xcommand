package org.xcommand.core;

public class ObjectIdentityKeyProvider implements IDynaBeanKeyProvider
{
	@Override
	public String getKey(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		return System.identityHashCode(aTargetObj) + aMethodInfo.property;
	}
}

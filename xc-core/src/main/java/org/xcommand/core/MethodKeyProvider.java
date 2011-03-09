package org.xcommand.core;

public class MethodKeyProvider implements IDynaBeanKeyProvider
{
	@Override
	public String getKey(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		return aMethodInfo.property;
	}
}
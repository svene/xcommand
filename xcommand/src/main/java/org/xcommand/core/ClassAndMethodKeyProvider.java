package org.xcommand.core;

public class ClassAndMethodKeyProvider implements IDynaBeanKeyProvider
{
	public String getKey(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs)
	{
		return aMethodInfo.methodClassName + "." + aMethodInfo.property;
	}
}
package org.xcommand.core;

import java.lang.reflect.Method;

class MethodInfo
{
	MethodInfo(Method aMethod)
	{
		method = aMethod;
		isSetter = aMethod.getName().startsWith("set");
		property = aMethod.getName().substring(3);
		methodClassName = aMethod.getDeclaringClass().getName();
	}

	final Method method;
	final boolean isSetter;
	final String methodClassName;
	final String property;
}

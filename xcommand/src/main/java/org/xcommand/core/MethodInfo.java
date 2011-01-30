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

	Method method;
	boolean isSetter;
	String methodClassName;
	String property;
}

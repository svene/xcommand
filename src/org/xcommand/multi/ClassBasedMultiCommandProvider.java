package org.xcommand.multi;

import java.lang.reflect.Method;
import java.util.Map;

public class ClassBasedMultiCommandProvider extends BaseMultiCommandProvider
{
// --- Initialization ---

	public ClassBasedMultiCommandProvider(Class aClazz)
	{
		setTargetClass(aClazz);
	}
	public ClassBasedMultiCommandProvider()
	{
	}

	public void init()
	{
		Method[] methods = targetClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method m = methods[i];
			Class[] classes = m.getParameterTypes();
			if (classes.length != 1) continue;
			if (!classes[0].isAssignableFrom(Map.class) ) continue;
			MethodCmd mc;
			mc = new MethodCmd(targetClass);
			mc.setMethod(m);
			commandMap.put(m.getName(), mc);
		}
	}

// --- Access ---

	public Class getTargetClass()
	{
		return targetClass;
	}

// --- Setting ---

	public void setTargetClass(Class aTargetClass)
	{
		targetClass = aTargetClass;
	}

// --- Implementation ---

	private Class targetClass;

}

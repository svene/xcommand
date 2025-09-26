package org.xcommand.core.multi;

import java.lang.reflect.Method;
import java.util.Map;

public class ClassBasedMultiCommandProvider extends BaseMultiCommandProvider {
	public ClassBasedMultiCommandProvider(Class<?> aClazz) {
		this.targetClass = aClazz;
	}

//	public ClassBasedMultiCommandProvider() {
//	}

	public void init() {
		Method[] methods = targetClass.getDeclaredMethods();
		for (Method m : methods) {
			Class<?>[] classes = m.getParameterTypes();
			if (classes.length != 1) {
				continue;
			}
			if (!classes[0].isAssignableFrom(Map.class)) {
				continue;
			}
			MethodCmd mc = MethodCmd.fromClass(targetClass, m);
			commandMap.put(m.getName(), mc);
		}
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> aTargetClass) {
		targetClass = aTargetClass;
	}

	private Class<?> targetClass;

}

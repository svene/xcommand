package org.xcommand.core.multi;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;

public class ObjectBasedMultiCommandProvider extends BaseMultiCommandProvider {

	public ObjectBasedMultiCommandProvider(Object aTargetObject) {
		this.targetObject = aTargetObject;
	}

	public void init() {
		commandMap = new HashMap<>();
		Class<?> clazz = targetObject.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			Class<?>[] classes = m.getParameterTypes();
			if (classes.length != 1) {
				continue;
			}
			if (!classes[0].isAssignableFrom(Map.class)) {
				continue;
			}
			MethodCmd mc = MethodCmd.fromObject(targetObject, m);
			commandMap.put(m.getName(), mc);
		}
	}


	public void setTargetObject(Object aTargetObject) {
		targetObject = aTargetObject;
	}

	private Object targetObject;

}

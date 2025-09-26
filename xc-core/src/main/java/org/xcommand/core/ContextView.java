package org.xcommand.core;

import org.jspecify.annotations.Nullable;

public abstract class ContextView
{

	public ContextView() {
		register();
	}

	protected void register() {
		String className = getClass().getName();
		System.out.println("className = " + className);
		TCP.getContext().put(className, this);
	}

	@Nullable
	public static ContextView getContextView(Class<?> aClass) {
		return (ContextView) TCP.getContext().get(aClass.getName());
	}

	public static void removeContextView(Class<?> aClass) {
		TCP.getContext().remove(aClass.getName());
	}

}

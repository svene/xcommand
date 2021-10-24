package org.xcommand.core;

public abstract class ContextView
{

	public ContextView()
	{
		register();
	}

	protected void register()
	{
		String className = getClass().getName();
		System.out.println("className = " + className);
		TCP.getContext().put(className, this);
	}

// --- Access ---

	public static ContextView getContextView(Class<?> aClass)
	{
		return (ContextView) TCP.getContext().get(aClass.getName());
	}

// --- Element change ---

	public static void removeContextView(Class<?> aClass)
	{
		TCP.getContext().remove(aClass.getName());
	}

}

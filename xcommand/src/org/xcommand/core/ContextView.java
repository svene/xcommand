package org.xcommand.core;

import java.util.Map;

public abstract class ContextView
{

// --- Initialization ---

	public ContextView(Map aCtx)
	{
		context = aCtx;
		register(aCtx);
	}

	protected void register(Map aContext)
	{
		String className = getClass().getName();
		System.out.println("className = " + className);
		context.put(className, this);
	}

// --- Access ---

	public static ContextView getContextView(Map aCtx, Class aClass)
	{
		return (ContextView) aCtx.get(aClass.getName());
	}

	public Map getContext()
	{
		return context;
	}

// --- Setting ---

	public void setContext(Map aContext)
	{
		context = aContext;
	}

// --- Element change ---

	public static void removeContextView(Map aCtx, Class aClass)
	{
		aCtx.remove(aClass.getName());
	}

// --- Implementation ---

	protected Map context;

}

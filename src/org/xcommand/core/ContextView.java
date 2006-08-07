package org.xcommand.core;

import java.util.Map;

public abstract class ContextView
{
// --- Access ---

	public Map getContext()
	{
		return context;
	}

// --- Setting ---

	public void setContext(Map aContext)
	{
		context = aContext;
	}

// --- Implementation ---

	protected Map context;

}

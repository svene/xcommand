package org.xcommand.example.xc110;

import java.util.Map;

public class DynamicContextView
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

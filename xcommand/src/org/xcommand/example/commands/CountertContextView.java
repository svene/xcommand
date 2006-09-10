package org.xcommand.example.commands;

import java.util.Map;

public class CountertContextView
{

// --- Access ---

	public static Integer getCounter(Map aContext)
	{
		return (Integer) aContext.get(CountertContextView.KEY_COUNTER);
	}

// --- Setting ---

	public static void setCounter(Map aContext, Integer aCounter)
	{
		aContext.put(CountertContextView.KEY_COUNTER, aCounter);
	}

// --- Implementation ---

	public static final String KEY_COUNTER = "counter";
}

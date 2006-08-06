package org.xcommand.core;

import java.util.Map;

public class ResultContextView
{

// --- Access ---

	public static Object getResult(Map aContext)
	{
		return aContext.get(ResultContextView.KEY_RESULT);
	}

// --- Setting ---

	public static void setResult(Map aContext, Object aValue)
	{
		aContext.put(ResultContextView.KEY_RESULT, aValue);
	}

// --- Implementation ---

	public static final String KEY_RESULT = "org.xcommand.core.ResultContextView.result";
}

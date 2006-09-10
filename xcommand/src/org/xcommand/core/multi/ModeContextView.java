package org.xcommand.core.multi;

import java.util.Map;

public class ModeContextView
{

// --- Access ---

	public static String getMode(Map aCtx)
	{
		return (String) aCtx.get(ModeContextView.KEY_MODE);
	}

// --- Setting ---

	public static void setMode(Map aCtx, String aMode)
	{
		aCtx.put(KEY_MODE, aMode);
	}

// --- Implementation ---

	public static final String NS = "org.xcommand.core.multi.ModeContextView.";
	public static final String KEY_MODE = ModeContextView.NS + "mode";
}

package org.xcommand.core.multi;

import org.xcommand.core.TCP;

/** TODO: think about how to replace this with IModeCV (since 'KEY_MODE' is used directly outside for example */
public class ModeContextView
{
	private ModeContextView() {
	}

// --- Access ---

	public static String getMode()
	{
		return (String) TCP.getContext().get(ModeContextView.KEY_MODE);
	}

	public static String getNewMode()
	{
		return (String) TCP.getContext().get(ModeContextView.KEY_NEW_MODE);
	}

// --- Setting ---

	public static void setMode(String aMode)
	{
		TCP.getContext().put(KEY_MODE, aMode);
	}

	public static void setNewMode(String aMode)
	{
		TCP.getContext().put(KEY_NEW_MODE, aMode);
	}

// --- Implementation ---

	public static final String NS = "org.xcommand.core.multi.ModeContextView.";
	public static final String KEY_MODE = NS + "mode";
	public static final String KEY_NEW_MODE = NS + "new_mode";
}

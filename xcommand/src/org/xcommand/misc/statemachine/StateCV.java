package org.xcommand.misc.statemachine;

import java.util.Map;

/**
 * Merge with TransitionCV to StateMachineCV
 */
public class StateCV
{

// --- Constants ---

	public static final String NS = "org.collage.sm.StateCV.";
	public static final String KEY_MODE = StateCV.NS + "mode";
	public static final String ENTER = NS + "ENTER";
	public static final String EXECUTE = NS + "EXECUTE";
	public static final String EXIT = NS + "EXIT";

// --- Access ---

	public static String getMode(Map aCtx)
	{
		return (String) aCtx.get(KEY_MODE);
	}

// --- Setting ---

	public static void setMode(Map aCtx, String aMode)
	{
		aCtx.put(KEY_MODE, aMode);
	}

}
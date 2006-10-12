package org.xcommand.misc.statemachine;

import java.util.Map;

/**
 * Merge with StateCV to StateMachineCV
 */
public class TransitionCV
{

// --- Constants ---

	public static final String NS = "org.collage.sm.TransitionCV";
	public static final String KEY_MODE = NS + "mode";
	public static final String ENTER = NS + "ENTER";
	public static final String EXECUTE = NS + "EXECUTE";
	public static final String EXIT = NS + "EXIT";

// --- Access ---

	public static String getMode(Map aCtx)
	{
		return (String) aCtx.get(TransitionCV.KEY_MODE);
	}

// --- Setting ---

	public static void setMode(Map aCtx, String aMode)
	{
		aCtx.put(TransitionCV.KEY_MODE, aMode);
	}

}

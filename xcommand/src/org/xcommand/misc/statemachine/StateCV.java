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
	private static final String KEY_STATE = NS + "STATE";

// --- Access ---

	/** @deprecated */
	public static String getMode(Map aCtx)
	{
		return (String) aCtx.get(KEY_MODE);
	}

	public static IState getState(Map aCtx)
	{
		return (IState) aCtx.get(KEY_STATE);
	}

// --- Setting ---

	/** @deprecated */
	public static void setMode(Map aCtx, String aMode)
	{
		aCtx.put(KEY_MODE, aMode);
	}

	public static void setState(Map aCtx, IState aState)
	{
		aCtx.put(KEY_STATE, aState);
	}


}

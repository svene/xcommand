package org.collage.dom.evaluator.common;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class StringHandlerCV
{

// --- Access ---

	public static IXCommand getStringHandlerCommand(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_STRING_HANDLER_COMMAND);
	}
	public static String getString(Map aCtx)
	{
		return (String) aCtx.get(KEY_STRING);
	}

// --- Setting ---

	public static void setStringHandlerCommand(Map aCtx, IXCommand aStringHandler)
	{
		aCtx.put(KEY_STRING_HANDLER_COMMAND, aStringHandler);
	}
	public static void setString(Map aCtx, String aString)
	{
		aCtx.put(KEY_STRING, aString);
	}

// --- Implementation ---

	private static final String NS = "org.collage.dom.evaluator.common.StringHandlerCV.";
	private static final String KEY_STRING_HANDLER_COMMAND = NS + "STRING_HANDLER_COMMAND";
	private static final String KEY_STRING = NS + "STRING";
}

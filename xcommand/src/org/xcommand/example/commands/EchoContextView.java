package org.xcommand.example.commands;

import java.util.Map;

public class EchoContextView
{

// --- Access ---

	public static String getMessage(Map aContext)
	{
		return (String) aContext.get(KEY_MESSAGE);
	}

// --- Setting ---

	public static void setMessage(Map aContext, String aMessage)
	{
		aContext.put(KEY_MESSAGE, aMessage);
	}

// --- Implementation ---

	public static final String KEY_MESSAGE = "message";
}

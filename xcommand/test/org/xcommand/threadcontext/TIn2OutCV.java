package org.xcommand.threadcontext;

public class TIn2OutCV
{

// --- Access ---

	public static String getInput()
	{
		return (String) CVHelper.get(KEY_INPUT);
	}
	public static String getOutput()
	{
		return (String) CVHelper.get(KEY_OUTPUT);
	}

// --- Setting ---

	public static void setInput(String aMessage)
	{
		CVHelper.set(KEY_INPUT, aMessage);
	}
	public static void setOutput(String aMessage)
	{
		CVHelper.set(KEY_OUTPUT, aMessage);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.threadcontext.TIn2OutCV.";
	private static final String KEY_INPUT = NS + "INPUT";
	private static final String KEY_OUTPUT = NS + "OUTPUT";

}

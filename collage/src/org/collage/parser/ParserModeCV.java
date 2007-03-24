package org.collage.parser;

import java.util.Map;

public class ParserModeCV
{

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

// --- Constants ---

	public static final String NS = "org.collage.parser.";
	public static final String KEY_MODE = NS + "mode";

	public static final String KEY_START_MODE = "start";
	public static final String KEY_VAR_START_MODE = "varstart";
	public static final String KEY_VAR_NAME_MODE = "varname";
	public static final String KEY_VAR_END_MODE = "varend";
	public static final String KEY_JAVA_START_MODE = "javastart";
	public static final String KEY_JAVA_CODE_MODE = "javaname";
	public static final String KEY_JAVA_END_MODE = "javaend";
	public static final String KEY_TEXT = "text";
	public static final String KEY_EOL = "eol";
	public static final String KEY_EOF = "eof";

}

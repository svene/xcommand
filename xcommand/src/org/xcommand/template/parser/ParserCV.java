package org.xcommand.template.parser;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

public class ParserCV
{

// --- Access ---

	public static InputStream getInputStream(Map aCtx)
	{
		return (InputStream) aCtx.get(ParserCV.KEY_INPUT_STREAM);
	}

	public static PrintStream getTraceStream(Map aCtx)
	{
		return (PrintStream) aCtx.get(ParserCV.KEY_TRACE_STREAM);
	}

	public static StringBuffer getStringBuffer(Map aCtx)
	{
		return (StringBuffer) aCtx.get(ParserCV.KEY_STRINGBUFFER);
	}

	public static String getValue(Map aCtx)
	{
		return (String) aCtx.get(ParserCV.KEY_VALUE);
	}

	public static Object getToken(Map aCtx)
	{
		return aCtx.get(ParserCV.KEY_TOKEN);
	}

// --- Setting ---

	public static void setInputStream(Map aCtx, InputStream aInputStream)
	{
		aCtx.put(ParserCV.KEY_INPUT_STREAM, aInputStream);
	}

	public static void setTraceStream(Map aCtx, PrintStream aPrintStream)
	{
		aCtx.put(ParserCV.KEY_TRACE_STREAM, aPrintStream);
	}

	public static void setStringBuffer(Map aCtx, StringBuffer aStringBuffer)
	{
		aCtx.put(ParserCV.KEY_STRINGBUFFER, aStringBuffer);
	}
	public static void setValue(Map aCtx, String aValue)
	{
		aCtx.put(ParserCV.KEY_VALUE, aValue);
	}
	public static void setToken(Map aCtx, Object aToken)
	{
		aCtx.put(ParserCV.KEY_TOKEN, aToken);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.template.parser.ParserCV.";
	public static final String KEY_INPUT_STREAM = ParserCV.NS + "INPUT_STREAM";
	public static final String KEY_TRACE_STREAM = ParserCV.NS + "TRACE_STREAM";
	public static final String KEY_STRINGBUFFER = ParserCV.NS + "STRINGBUFFER";
	public static final String KEY_VALUE = ParserCV.NS + "VALUE";
	public static final String KEY_TOKEN = ParserCV.NS + "TOKEN";
}

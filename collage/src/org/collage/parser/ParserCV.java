package org.collage.parser;

import org.xcommand.core.IXCommand;

import java.util.Map;
import java.io.PrintStream;
import java.io.InputStream;

public class ParserCV
{

// --- Access ---

	public static InputStream getInputStream(Map aCtx)
	{
		return (InputStream) aCtx.get(KEY_INPUT_STREAM);
	}

	public static PrintStream getTraceStream(Map aCtx)
	{
		return (PrintStream) aCtx.get(KEY_TRACE_STREAM);
	}

	public static StringBuffer getStringBuffer(Map aCtx)
	{
		return (StringBuffer) aCtx.get(KEY_STRINGBUFFER);
	}

	public static String getValue(Map aCtx)
	{
		return (String) aCtx.get(KEY_VALUE);
	}

	public static Object getToken(Map aCtx)
	{
		return aCtx.get(KEY_TOKEN);
	}

// --- Setting ---

	public static void setInputStream(Map aCtx, InputStream aInputStream)
	{
		aCtx.put(KEY_INPUT_STREAM, aInputStream);
	}

	public static void setTraceStream(Map aCtx, PrintStream aPrintStream)
	{
		aCtx.put(KEY_TRACE_STREAM, aPrintStream);
	}

	public static void setStringBuffer(Map aCtx, StringBuffer aStringBuffer)
	{
		aCtx.put(KEY_STRINGBUFFER, aStringBuffer);
	}
	public static void setValue(Map aCtx, String aValue)
	{
		aCtx.put(KEY_VALUE, aValue);
	}
	public static void setToken(Map aCtx, Object aToken)
	{
		aCtx.put(KEY_TOKEN, aToken);
	}

// --- Implementation ---

	public static final String NS = "org.collage.parser.ParserCV.";
	public static final String KEY_INPUT_STREAM = NS + "INPUT_STREAM";
	public static final String KEY_TRACE_STREAM = NS + "TRACE_STREAM";
	public static final String KEY_STRINGBUFFER = NS + "STRINGBUFFER";
	public static final String KEY_VALUE = NS + "VALUE";
	public static final String KEY_TOKEN = NS + "TOKEN";
}

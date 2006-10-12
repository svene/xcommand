package org.collage.parser;

import org.xcommand.core.IXCommand;

import java.util.Map;
import java.io.PrintStream;
import java.io.InputStream;

public class ParserContextView
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

	public static IXCommand getTokenHandlerDispatcher(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_TOKEN_HANDLER_DISPATCHER);
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
	public static IXCommand getStateSwitchHandler(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_STATE_SWITCH_HANDLER);
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

	public static void setTokenHandlerDispatcher(Map aCtx, IXCommand aScannerHandler)
	{
		aCtx.put(KEY_TOKEN_HANDLER_DISPATCHER, aScannerHandler);
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

	public static void setStateSwitchHandler(Map aCtx, IXCommand aStateSwitchHandler)
	{
		aCtx.put(KEY_STATE_SWITCH_HANDLER, aStateSwitchHandler);
	}

// --- Implementation ---

	public static final String NS = "org.collage.parser.ParserContextView.";
	public static final String KEY_INPUT_STREAM = NS + "INPUT_STREAM";
	public static final String KEY_TRACE_STREAM = NS + "TRACE_STREAM";
	public static final String KEY_TOKEN_HANDLER_DISPATCHER = NS + "TOKEN_HANDLER_DISPATCHER";
	public static final String KEY_STRINGBUFFER = NS + "STRINGBUFFER";
	public static final String KEY_VALUE = NS + "VALUE";
	public static final String KEY_TOKEN = NS + "TOKEN";
	public static final String KEY_STATE_SWITCH_HANDLER = NS + "STATE_SWITCH_HANDLER";
}
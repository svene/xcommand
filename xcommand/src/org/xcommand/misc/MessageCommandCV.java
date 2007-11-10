package org.xcommand.misc;

import java.util.Map;
import java.util.List;
import java.io.PrintWriter;

public class MessageCommandCV
{

// --- Access ---

	public static PrintWriter getPrintWriter(Map aCtx)
	{
		return (PrintWriter) aCtx.get(KEY_PRINT_WRITER);
	}

	public static List getList(Map aCtx)
	{
		return (List) aCtx.get(KEY_LIST);
	}

// --- Setting ---

	public static void setPrintWriter(Map aCtx, PrintWriter aPrintWriter)
	{
		aCtx.put(KEY_PRINT_WRITER, aPrintWriter);
	}

	public static void setList(Map aCtx, List aList)
	{
		aCtx.put(KEY_LIST, aList);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.misc.MessageCommandCV.";
	private static final String KEY_PRINT_WRITER = NS + "PRINT_WRITER";
	private static final String KEY_LIST = NS + "LIST";
}

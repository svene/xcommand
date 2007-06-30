package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

import java.util.Map;

/**
 * ContextView to access a handler
 */
public class HandlerCV
{

// --- Access ---

	public static IXCommand getHandler(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_HANDLER);
	}

// --- Setting ---

	public static void setHandler(Map aCtx, IXCommand aHandler)
	{
		aCtx.put(KEY_HANDLER, aHandler);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.datastructure.tree.HandlerCV.";
	private static final String KEY_HANDLER = NS + "HANDLER";
}

package org.xcommand.example.xc110;

import java.util.Map;

public class EchoContextView
{

// --- Access ---

	public static DynamicEchoContextView getContextView(Map aCtx)
	{
		return (DynamicEchoContextView) aCtx.get(KEY_CONTEXT_VIEW);
	}

// --- Setting ---

	public static void setContextView(Map aCtx, DynamicEchoContextView aContextView)
	{
		aCtx.put(KEY_CONTEXT_VIEW, aContextView);
		aContextView.setContext(aCtx);
	}

// --- Element change ---

	public static void removeContextView(Map aCtx)
	{
		aCtx.remove(KEY_CONTEXT_VIEW);
	}

// --- Implementation ---

	public static final String NS = "org.xcommand.example.xc110.";
	public static final String KEY_CONTEXT_VIEW = NS + "CONTEXT_VIEW";
}

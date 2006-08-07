package org.xcommand.example.xc110;

import org.xcommand.core.ContextView;
import org.xcommand.example.commands.EchoContextView;

import java.util.Map;

public class DynamicEchoContextView extends ContextView
{

// --- Access ---

	public String getMessage()
	{
		return EchoContextView.getMessage(context);
	}
	public static DynamicEchoContextView getContextView(Map aCtx)
	{
		return (DynamicEchoContextView) aCtx.get(KEY_CONTEXT_VIEW);
	}

// --- Setting ---

	public void setMessage(String aMessage)
	{
		EchoContextView.setMessage(context, aMessage);
	}
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
	public static final String KEY_MESSAGE = NS + "message";

}

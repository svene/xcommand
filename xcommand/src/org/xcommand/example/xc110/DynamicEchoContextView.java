package org.xcommand.example.xc110;

import org.xcommand.core.ContextView;
import org.xcommand.example.commands.EchoContextView;

import java.util.Map;

public class DynamicEchoContextView extends ContextView
{

// --- Initialization ---

	public DynamicEchoContextView(Map aCtx)
	{
		super(aCtx);
	}

// --- Access ---

	public String getMessage()
	{
		return EchoContextView.getMessage(context);
	}

	public static DynamicEchoContextView getContextView(Map aCtx)
	{
		return (DynamicEchoContextView) getContextView(aCtx, DynamicEchoContextView.class);
	}

// --- Setting ---

	public void setMessage(String aMessage)
	{
		EchoContextView.setMessage(context, aMessage);
	}

// --- Implementation ---

	public static final String NS = DynamicEchoContextView.class.getName() + ".";
	public static final String KEY_CONTEXT_VIEW = NS + "CONTEXT_VIEW";
	public static final String KEY_MESSAGE = NS + "message";
}

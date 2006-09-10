package org.collage.dom.creationhandler;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class DomNodeCreationHandlerContextView
{

// --- Access ---

	public static String getValue(Map aCtx)
	{
		return (String) aCtx.get(DomNodeCreationHandlerContextView.KEY_VALUE);
	}

	public static IXCommand getDomNodeCreationHandler(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_DOM_NODE_CREATION_HANDLER);
	}

	public static Boolean getProduceJavaSource(Map aCtx)
	{
		return (Boolean) aCtx.get(KEY_PRODUCE_JAVA_SOURCE);
	}

// --- Setting ---

	public static void setValue(Map aCtx, String aValue)
	{
		aCtx.put(DomNodeCreationHandlerContextView.KEY_VALUE, aValue);
	}

	public static void setProduceJavaSource(Map aCtx, Boolean aProduceJavaSource)
	{
		aCtx.put(KEY_PRODUCE_JAVA_SOURCE, aProduceJavaSource);
	}

	public static void setDomNodeCreationHandler(Map aCtx, IXCommand aDomNodeCreationHandler)
	{
		aCtx.put(KEY_DOM_NODE_CREATION_HANDLER, aDomNodeCreationHandler);
	}

// --- Implementation ---

	public static final String NS = "org.collage.dom.creationhandler.DomNodeCreationHandlerContextView.";
	public static final String KEY_VALUE = DomNodeCreationHandlerContextView.NS + "VALUE";
	public static final String KEY_PRODUCE_JAVA_SOURCE = NS + "PRODUCE_JAVA_SOURCE";
	public static final String KEY_DOM_NODE_CREATION_HANDLER = NS + "DOM_NODE_CREATION_HANDLER";
}

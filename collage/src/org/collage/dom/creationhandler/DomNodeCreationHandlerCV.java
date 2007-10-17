package org.collage.dom.creationhandler;

import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.ISubject;

import java.util.Map;

public class DomNodeCreationHandlerCV
{

// --- Access ---

	public static String getValue(Map aCtx)
	{
		return (String) aCtx.get(KEY_VALUE);
	}

	/** @deprecated */
	public static IXCommand getDomNodeCreationHandler(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_DOM_NODE_CREATION_HANDLER);
	}

	public static Boolean getProduceJavaSource(Map aCtx)
	{
		return (Boolean) aCtx.get(KEY_PRODUCE_JAVA_SOURCE);
	}

	public static ISubject getCreateTextNodeRequestSubject(Map aCtx)
	{
		return (ISubject) aCtx.get(KEY_CREATE_TEXT_NODE_REQUEST_SUBJECT);
	}
	public static ISubject getCreateVariableNodeRequestSubject(Map aCtx)
	{
		return (ISubject) aCtx.get(KEY_CREATE_VARIABLE_NODE_REQUEST_SUBJECT);
	}
	public static ISubject getCreateJavaNodeRequestSubject(Map aCtx)
	{
		return (ISubject) aCtx.get(KEY_CREATE_JAVA_NODE_REQUEST_SUBJECT);
	}
	public static ISubject getCreateRootNodeRequestSubject(Map aCtx)
	{
		return (ISubject) aCtx.get(KEY_CREATE_ROOT_NODE_REQUEST_SUBJECT);
	}

// --- Setting ---

	public static void setValue(Map aCtx, String aValue)
	{
		aCtx.put(KEY_VALUE, aValue);
	}

	public static void setProduceJavaSource(Map aCtx, Boolean aProduceJavaSource)
	{
		aCtx.put(KEY_PRODUCE_JAVA_SOURCE, aProduceJavaSource);
	}

	/** @deprecated */
	public static void setDomNodeCreationHandler(Map aCtx, IXCommand aDomNodeCreationHandler)
	{
		aCtx.put(KEY_DOM_NODE_CREATION_HANDLER, aDomNodeCreationHandler);
	}
	public static void setCreateTextNodeRequestSubject(Map aCtx, ISubject aSubject)
	{
		aCtx.put(KEY_CREATE_TEXT_NODE_REQUEST_SUBJECT, aSubject);
	}
	public static void setCreateVariableNodeRequestSubject(Map aCtx, ISubject aSubject)
	{
		aCtx.put(KEY_CREATE_VARIABLE_NODE_REQUEST_SUBJECT, aSubject);
	}
	public static void setCreateJavaNodeRequestSubject(Map aCtx, ISubject aSubject)
	{
		aCtx.put(KEY_CREATE_JAVA_NODE_REQUEST_SUBJECT, aSubject);
	}
	public static void setCreateRootNodeRequestSubject(Map aCtx, ISubject aSubject)
	{
		aCtx.put(KEY_CREATE_ROOT_NODE_REQUEST_SUBJECT, aSubject);
	}


// --- Implementation ---

	public static final String NS = "org.collage.dom.creationhandler.DomNodeCreationHandlerCV.";
	public static final String KEY_VALUE = NS + "VALUE";
	public static final String KEY_PRODUCE_JAVA_SOURCE = NS + "PRODUCE_JAVA_SOURCE";
	public static final String KEY_DOM_NODE_CREATION_HANDLER = NS + "DOM_NODE_CREATION_HANDLER";

	// subjects
	private static final String KEY_CREATE_TEXT_NODE_REQUEST_SUBJECT = NS + "TEXT_NODE_CREATION_REQUEST_SUBJECT";
	private static final String KEY_CREATE_VARIABLE_NODE_REQUEST_SUBJECT = NS + "VARIABLE_NODE_CREATION_REQUEST_SUBJECT";
	private static final String KEY_CREATE_JAVA_NODE_REQUEST_SUBJECT = NS + "JAVA_NODE_CREATION_REQUEST_SUBJECT";
	private static final String KEY_CREATE_ROOT_NODE_REQUEST_SUBJECT = NS + "ROOT_NODE_CREATION_REQUEST_SUBJECT";

}

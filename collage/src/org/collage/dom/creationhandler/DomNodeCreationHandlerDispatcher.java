package org.collage.dom.creationhandler;

import org.collage.dom.creationhandler.DomNodeCreationHandlerModeContextView;
import org.collage.dom.creationhandler.RootNodeCreationHandler;
import org.collage.dom.creationhandler.TextNodeCreationHandler;
import org.collage.dom.creationhandler.VariableNodeCreationHandler;
import org.xcommand.core.multi.ModeBasedCommandDispatcher;

import java.util.Map;
import java.util.HashMap;

public class DomNodeCreationHandlerDispatcher extends ModeBasedCommandDispatcher
{

// --- Initialization ---

	public DomNodeCreationHandlerDispatcher()
	{
		setModeCommandMap(configCtx);
	}

// --- Implementation ---

	private static Map configCtx = new HashMap();

	static
	{
		configCtx.put(DomNodeCreationHandlerModeContextView.KEY_START, new RootNodeCreationHandler());
		configCtx.put(DomNodeCreationHandlerModeContextView.KEY_TEXT, new TextNodeCreationHandler());
		configCtx.put(DomNodeCreationHandlerModeContextView.KEY_VARIABLE, new VariableNodeCreationHandler());
		configCtx.put(DomNodeCreationHandlerModeContextView.KEY_JAVA_CODE, new JavaNodeCreationHandler());
	}
}

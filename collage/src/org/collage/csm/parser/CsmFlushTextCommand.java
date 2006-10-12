package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;
import org.collage.parser.ParserContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;

import java.util.Map;

/**
 * Commands flushing buffered text and creating associated Text-DOM-Node
 */
public class CsmFlushTextCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		// Get String from Stringbuffer:
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String s = sb.toString();

		// Create a Text-DOM-Node:
		ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeContextView.KEY_TEXT);//!!mode
		DomNodeCreationHandlerContextView.setValue(aCtx, s);
		IXCommand dnch = DomNodeCreationHandlerContextView.getDomNodeCreationHandler(aCtx);
		dnch.execute(aCtx);
	}
}

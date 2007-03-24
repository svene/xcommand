package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;
import org.collage.parser.ParserCV;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeCV;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;

import java.util.Map;

/**
 * Commands flushing buffered text and creating associated Text-DOM-Node
 */
public class CsmFlushTextCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		// Get String from Stringbuffer:
		StringBuffer sb = ParserCV.getStringBuffer(aCtx);
		String s = sb.toString();

		// Create a Text-DOM-Node:
		ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeCV.KEY_TEXT);//!!mode
		DomNodeCreationHandlerCV.setValue(aCtx, s);
		IXCommand dnch = DomNodeCreationHandlerCV.getDomNodeCreationHandler(aCtx);
		dnch.execute(aCtx);
	}
}

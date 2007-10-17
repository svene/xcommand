package org.collage.csm.parser;

import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;

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
		DomNodeCreationHandlerCV.setValue(aCtx, s);
		DomNodeCreationHandlerCV.getCreateTextNodeRequestSubject(aCtx).execute(aCtx);
	}
}

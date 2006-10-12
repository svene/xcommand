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
public class CsmFlushJavaCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String s = sb.toString();
		if (s.length() > 0)
		{
			ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeContextView.KEY_JAVA_CODE);
			DomNodeCreationHandlerContextView.setValue(aCtx, s);
			IXCommand dnch = DomNodeCreationHandlerContextView.getDomNodeCreationHandler(aCtx);
			dnch.execute(aCtx);
		}
	}
}

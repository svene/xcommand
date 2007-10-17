package org.collage.csm.parser;

import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;

import java.util.Map;

/**
 * Commands flushing buffered text and creating associated Text-DOM-Node
 */
public class CsmFlushJavaCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserCV.getStringBuffer(aCtx);
		String s = sb.toString();
		if (s.length() > 0)
		{
			DomNodeCreationHandlerCV.setValue(aCtx, s);
			DomNodeCreationHandlerCV.getCreateJavaNodeRequestSubject(aCtx).execute(aCtx);
		}
	}
}

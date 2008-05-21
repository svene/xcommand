package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

/**
 * Commands flushing buffered text and creating associated Text-DOM-Node
 */
public class CsmFlushTextCommand implements ICommand
{
	public void execute()
	{
		// Get String from Stringbuffer:
		StringBuffer sb = parserCV.getStringBuffer();
		String s = sb.toString();

		// Create a Text-DOM-Node:
		domNodeCreationHandlerCV.setValue(s);
		domNodeCreationHandlerCV.getCreateTextNodeRequestNotifier().execute();
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.getBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
}

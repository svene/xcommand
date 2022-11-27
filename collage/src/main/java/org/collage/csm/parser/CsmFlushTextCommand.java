package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

/**
 * Commands flushing buffered text and creating associated Text-DOM-Node
 */
public class CsmFlushTextCommand implements ICommand {
	@Override
	public void execute() {
		// Get String from Stringbuffer:
		StringBuffer sb = parserCV.getStringBuffer();
		String s = sb.toString();

		// Create a Text-DOM-Node:
		domNodeCreationHandlerCV.setValue(s);
		domNodeCreationHandlerCV.getCreateTextNodeRequestNotifier().execute();
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

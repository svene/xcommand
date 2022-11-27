package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

/**
 * Commands flushing buffered text and creating associated Text-DOM-Node
 */
public class CsmFlushJavaCommand implements ICommand {
	@Override
	public void execute() {
		StringBuffer sb = parserCV.getStringBuffer();
		String s = sb.toString();
		if (s.length() > 0) {
			domNodeCreationHandlerCV.setValue(s);
			domNodeCreationHandlerCV.getCreateJavaNodeRequestNotifier().execute();
		}
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

/**
 * Commands flushing buffered text and creating associated Text-DOM-Node
 */
public class CsmFlushJavaCommand implements ICommand
{
	public void execute()
	{
		StringBuffer sb = parserCV.getStringBuffer();
		String s = sb.toString();
		if (s.length() > 0)
		{
			domNodeCreationHandlerCV.setValue(s);
			domNodeCreationHandlerCV.getCreateJavaNodeRequestNotifier().execute();
		}
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.getBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
}

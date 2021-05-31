package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmCreateVariableDomNodeCommand implements ICommand
{
	public void execute()
	{
		String value = parserCV.getValue();
		domNodeCreationHandlerCV.setValue(value);
		domNodeCreationHandlerCV.getCreateVariableNodeRequestNotifier().execute();
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);

}

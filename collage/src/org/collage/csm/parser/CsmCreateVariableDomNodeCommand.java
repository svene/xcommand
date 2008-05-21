package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmCreateVariableDomNodeCommand implements ICommand
{
	public void execute()
	{
		String value = parserCV.getValue();
		domNodeCreationHandlerCV.setValue(value);
		domNodeCreationHandlerCV.getCreateVariableNodeRequestNotifier().execute();
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.getBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);

}

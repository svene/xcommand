package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmCreateVariableDomNodeCommand implements ICommand
{
	@Override
	public void execute()
	{
		String value = parserCV.getValue();
		domNodeCreationHandlerCV.setValue(value);
		domNodeCreationHandlerCV.getCreateVariableNodeRequestNotifier().execute();
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);

}

package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmAppendEolCommand implements ICommand
{
	public void execute()
	{
		StringBuffer sb = parserCV.getStringBuffer();
//		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");

		Boolean javaMode = domNodeCreationHandlerCV.getProduceJavaSource();
		if (javaMode == Boolean.TRUE)
		{
//			sb.append("*PJS*");
			sb.append('\\');
			sb.append('n');
		}
		else
		{
//			sb.append("!PJS!");
			sb.append("\n");
		}
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.getBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}

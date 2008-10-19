package org.collage.csm.parser;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmAppendTextCommand implements ICommand
{
	public void execute()
	{
		StringBuffer sb = parserCV.getStringBuffer();
		String value = parserCV.getValue();
//		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");
		sb.append(value);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
}

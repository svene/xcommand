package org.collage.csm.parser;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
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
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

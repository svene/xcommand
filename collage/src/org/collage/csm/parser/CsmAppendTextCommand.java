package org.collage.csm.parser;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
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
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
}

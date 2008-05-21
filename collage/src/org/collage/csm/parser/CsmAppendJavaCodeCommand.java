package org.collage.csm.parser;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmAppendJavaCodeCommand implements ICommand
{
	public void execute()
	{
		StringBuffer sb = parserCV.getStringBuffer();
		String value = parserCV.getValue();
		sb.append(value);
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
}

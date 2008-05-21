package org.collage.csm.parser;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmStartTextCommand implements ICommand
{
	public void execute()
	{
		parserCV.setStringBuffer(new StringBuffer());
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
}

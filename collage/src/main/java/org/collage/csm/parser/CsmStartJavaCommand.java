package org.collage.csm.parser;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmStartJavaCommand implements ICommand
{
	public void execute()
	{
		parserCV.setStringBuffer(new StringBuffer());
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

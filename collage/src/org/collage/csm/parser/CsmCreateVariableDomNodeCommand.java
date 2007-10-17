package org.collage.csm.parser;

import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;

import java.util.Map;

public class CsmCreateVariableDomNodeCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		String value = ParserCV.getValue(aCtx);
		DomNodeCreationHandlerCV.setValue(aCtx, value);
		DomNodeCreationHandlerCV.getCreateVariableNodeRequestSubject(aCtx).execute(aCtx);
	}
}

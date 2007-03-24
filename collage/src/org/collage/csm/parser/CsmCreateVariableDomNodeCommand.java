package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;
import org.collage.parser.ParserCV;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeCV;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;

import java.util.Map;

public class CsmCreateVariableDomNodeCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		String value = ParserCV.getValue(aCtx);
		ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeCV.KEY_VARIABLE);
		DomNodeCreationHandlerCV.setValue(aCtx, value);
		IXCommand dnch = DomNodeCreationHandlerCV.getDomNodeCreationHandler(aCtx);
		dnch.execute(aCtx);
	}
}

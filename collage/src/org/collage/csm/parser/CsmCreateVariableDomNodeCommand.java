package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;
import org.collage.parser.ParserContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;

import java.util.Map;

public class CsmCreateVariableDomNodeCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		String value = ParserContextView.getValue(aCtx);
		ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeContextView.KEY_VARIABLE);
		DomNodeCreationHandlerContextView.setValue(aCtx, value);
		IXCommand dnch = DomNodeCreationHandlerContextView.getDomNodeCreationHandler(aCtx);
		dnch.execute(aCtx);
	}
}

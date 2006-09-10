package org.collage.parser;

import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeContextView;
import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;

import java.util.Map;

public class StartTokenHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		ParserContextView.setStringBuffer(aCtx, new StringBuffer());
		ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeContextView.KEY_START);
		IXCommand dnch = DomNodeCreationHandlerContextView.getDomNodeCreationHandler(aCtx);
		dnch.execute(aCtx);
	}
}

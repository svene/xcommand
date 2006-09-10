package org.collage.parser;

import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeContextView;
import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;

import java.util.Map;

public class EofTokenHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String s = sb.toString();
		ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeContextView.KEY_TEXT);
		DomNodeCreationHandlerContextView.setValue(aCtx, s);
		IXCommand dnch = DomNodeCreationHandlerContextView.getDomNodeCreationHandler(aCtx);
		dnch.execute(aCtx);
		ParserContextView.setStringBuffer(aCtx, new StringBuffer());
	}
}

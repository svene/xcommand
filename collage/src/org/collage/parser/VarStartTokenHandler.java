package org.collage.parser;

import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;

import java.util.Map;

public class VarStartTokenHandler implements IXCommand
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

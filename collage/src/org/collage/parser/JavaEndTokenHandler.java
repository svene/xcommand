package org.collage.parser;

import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerModeContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;

import java.util.Map;

public class JavaEndTokenHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String s = sb.toString();
		if (s.length() > 0)
		{
			ModeContextView.setMode(aCtx, DomNodeCreationHandlerModeContextView.KEY_JAVA_CODE);
			DomNodeCreationHandlerContextView.setValue(aCtx, s);
			IXCommand dnch = DomNodeCreationHandlerContextView.getDomNodeCreationHandler(aCtx);
			dnch.execute(aCtx);
			ParserContextView.setStringBuffer(aCtx, new StringBuffer());
		}
	}
}

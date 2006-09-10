package org.collage.parser;

import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.xcommand.core.IXCommand;

import java.util.Map;

public class EolTokenHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		Boolean javaMode = DomNodeCreationHandlerContextView.getProduceJavaSource(aCtx);
		if (javaMode == Boolean.TRUE)
		{
//			sb.append("*PJS*");
			sb.append('\\');
			sb.append('n');
		}
		else
		{
//			sb.append("!PJS!");
			sb.append("\n");
		}
	}
}

package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.collage.parser.ParserContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;

import java.util.Map;

public class CsmAppendEolCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
//!!		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");

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

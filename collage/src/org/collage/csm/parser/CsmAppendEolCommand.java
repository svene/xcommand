package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.collage.parser.ParserCV;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;

import java.util.Map;

public class CsmAppendEolCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserCV.getStringBuffer(aCtx);
//		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");

		Boolean javaMode = DomNodeCreationHandlerCV.getProduceJavaSource(aCtx);
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

package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.collage.parser.ParserContextView;

import java.util.Map;

public class CsmAppendTextCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String value = ParserContextView.getValue(aCtx);
//!!		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");
		sb.append(value);
	}
}

package org.collage.parser;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class TextTokenHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String value = ParserContextView.getValue(aCtx);
//!!		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");
		sb.append(value);
	}
}

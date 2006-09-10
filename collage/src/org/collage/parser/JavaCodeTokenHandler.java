package org.collage.parser;

import org.xcommand.core.IXCommand;
import org.xcommand.core.multi.ModeContextView;

import java.util.Map;

public class JavaCodeTokenHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String value = ParserContextView.getValue(aCtx);
		sb.append(value);
//!!		System.out.println("*** JavaCodeTokenHandler.execute: buffer '" + sb.toString() + "'");
	}
}

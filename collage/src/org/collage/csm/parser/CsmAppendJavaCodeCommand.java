package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.collage.parser.ParserContextView;

import java.util.Map;

public class CsmAppendJavaCodeCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserContextView.getStringBuffer(aCtx);
		String value = ParserContextView.getValue(aCtx);
		sb.append(value);
	}
}

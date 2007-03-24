package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.collage.parser.ParserCV;

import java.util.Map;

public class CsmAppendJavaCodeCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		StringBuffer sb = ParserCV.getStringBuffer(aCtx);
		String value = ParserCV.getValue(aCtx);
		sb.append(value);
	}
}

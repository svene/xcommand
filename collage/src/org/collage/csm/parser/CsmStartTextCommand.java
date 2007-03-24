package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.collage.parser.ParserCV;

import java.util.Map;

public class CsmStartTextCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		ParserCV.setStringBuffer(aCtx, new StringBuffer());
	}
}

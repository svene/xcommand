package org.collage.csm.parser;

import org.xcommand.core.IXCommand;
import org.collage.parser.ParserContextView;

import java.util.Map;

public class CsmStartJavaCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		ParserContextView.setStringBuffer(aCtx, new StringBuffer());
	}
}

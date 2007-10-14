package org.collage.dom.evaluator.common;

import org.xcommand.core.IXCommand;
import org.collage.dom.evaluator.common.StringHandlerCommand;
import org.collage.dom.ast.TextCV;

import java.util.Map;

// TODO: remove
public class StringHandlingTextHandler extends StringHandlingHandler
{

// --- Initialization ---

	public StringHandlingTextHandler(StringHandlerCommand aStringHandlerCommand)
	{
		super(aStringHandlerCommand);
	}

// --- Implementation ---

	protected String getOriginalText(Map aCtx)
	{
		return TextCV.getText(aCtx).getValue();
	}
}

package org.collage.dom.evaluator.common;

import org.collage.dom.ast.JavaCV;

import java.util.Map;

public class StringHandlingJavaHandler extends StringHandlingHandler
{

// --- Initialization ---

	public StringHandlingJavaHandler(StringHandlerCommand aStringHandlerCommand)
	{
		super(aStringHandlerCommand);
	}

// --- Implementation ---

	protected String getOriginalText(Map aCtx)
	{
		return JavaCV.getJava(aCtx).getValue();
	}
}

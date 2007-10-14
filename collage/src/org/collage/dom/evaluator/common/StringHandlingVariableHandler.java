package org.collage.dom.evaluator.common;

import org.collage.dom.ast.Variable;
import org.collage.dom.ast.VariableCV;

import java.util.Map;

//TODO: remove
public class StringHandlingVariableHandler extends StringHandlingHandler
{

// --- Initialization ---

	public StringHandlingVariableHandler(StringHandlerCommand aStringHandlerCommand)
	{
		super(aStringHandlerCommand);
	}

// --- Implementation ---

	protected String getOriginalText(Map aCtx)
	{
		Variable v = VariableCV.getVariable(aCtx);
		return v.getVariableName();
	}
}

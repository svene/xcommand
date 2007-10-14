package org.collage.dom.evaluator.common;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.Variable;
import org.collage.dom.ast.VariableCV;

import java.util.Map;

/**
 * Read VariableName for Variable on aCtx and put it on aCtx via `StringHandlerCV.setString' 
 */
public class VariableToVariableNameExtractor implements IXCommand
{
	public void execute(Map aCtx)
	{
		Variable v = VariableCV.getVariable(aCtx);
		StringHandlerCV.setString(aCtx, v.getVariableName());
	}
}

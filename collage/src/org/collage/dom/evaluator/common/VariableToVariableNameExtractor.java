package org.collage.dom.evaluator.common;

import org.collage.dom.ast.Variable;
import org.collage.dom.ast.IVariableCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

/**
 * Read VariableName for Variable on aCtx and put it on aCtx via `StringHandlerCV.setString' 
 */
public class VariableToVariableNameExtractor implements ICommand
{
	public void execute()
	{
		Variable v = variableCV.getVariable();
		stringHandlerCV.setString(v.getVariableName());
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IVariableCV variableCV = (IVariableCV) dbp.getBeanForInterface(IVariableCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
}

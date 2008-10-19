package org.collage.dom.evaluator.common;

import org.collage.dom.ast.Variable;
import org.collage.dom.ast.IVariableCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

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
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	IVariableCV variableCV = (IVariableCV) dbp.newBeanForInterface(IVariableCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}

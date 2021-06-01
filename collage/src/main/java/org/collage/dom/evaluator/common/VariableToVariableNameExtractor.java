package org.collage.dom.evaluator.common;

import org.collage.dom.ast.Variable;
import org.collage.dom.ast.IVariableCV;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

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
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IVariableCV variableCV = dbp.newBeanForInterface(IVariableCV.class);
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}

package org.collage.dom.evaluator.domdumper;

import org.collage.dom.ast.VariableNode;
import org.collage.dom.evaluator.EvaluationContextView;
import org.xcommand.core.IXCommand;
import java.util.Map;

public class VariableNodeDumper implements IXCommand
{
	public void execute(Map aCtx)
	{
		VariableNode node = (VariableNode) EvaluationContextView.getNode(aCtx);
		System.out.println("@@@ VARIABLE: '" + node.getVariableName() + "'");
	}
}

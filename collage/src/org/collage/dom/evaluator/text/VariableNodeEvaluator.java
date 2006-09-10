package org.collage.dom.evaluator.text;

import org.collage.dom.ast.VariableNode;
import org.collage.dom.evaluator.EvaluationContextView;
import org.xcommand.core.IXCommand;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class VariableNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		VariableNode node = (VariableNode) EvaluationContextView.getNode(aCtx);
		Writer writer = EvaluationContextView.getWriter(aCtx);
		try
		{
			String vn = node.getVariableName();
			Object obj = aCtx.get(vn);
			if (obj != null)
			{
				writer.write(obj.toString());
			}
			else
			{
				writer.write("${");
				writer.write(vn);
				writer.write('}');
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}

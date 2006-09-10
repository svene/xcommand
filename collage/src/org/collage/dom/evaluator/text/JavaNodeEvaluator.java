package org.collage.dom.evaluator.text;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.JavaNode;
import org.collage.dom.evaluator.EvaluationContextView;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class JavaNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		JavaNode node = (JavaNode) EvaluationContextView.getNode(aCtx);
		Writer writer = EvaluationContextView.getWriter(aCtx);
		try
		{
			writer.write(node.getValue());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}

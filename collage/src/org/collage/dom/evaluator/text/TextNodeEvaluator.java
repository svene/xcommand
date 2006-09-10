package org.collage.dom.evaluator.text;

import org.collage.dom.ast.TextNode;
import org.collage.dom.evaluator.EvaluationContextView;
import org.xcommand.core.IXCommand;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class TextNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		TextNode node = (TextNode) EvaluationContextView.getNode(aCtx);
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

package org.collage.dom.evaluator.text;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.JavaNode;
import org.collage.dom.evaluator.EvaluationCV;

import java.util.Map;
import java.io.Writer;
import java.io.IOException;

public class JavaNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		JavaNode node = (JavaNode) EvaluationCV.getNode(aCtx);
		Writer writer = EvaluationCV.getWriter(aCtx);
		try
		{
			writer.write("<?java");
			writer.write(node.getValue());
			writer.write("?>");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}

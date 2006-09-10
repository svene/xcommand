package org.collage.dom.evaluator.domdumper;

import org.collage.dom.ast.TextNode;
import org.collage.dom.evaluator.EvaluationContextView;
import org.xcommand.core.IXCommand;

import java.util.Map;

public class TextNodeDumper implements IXCommand
{
	public void execute(Map aCtx)
	{
		TextNode node = (TextNode) EvaluationContextView.getNode(aCtx);
		System.out.println("@@@ TEXT: '" + node.getValue() + "'");
	}
}

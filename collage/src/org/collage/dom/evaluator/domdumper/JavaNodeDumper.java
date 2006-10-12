package org.collage.dom.evaluator.domdumper;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.JavaNode;
import org.collage.dom.evaluator.EvaluationContextView;

import java.util.Map;

public class JavaNodeDumper implements IXCommand
{
	public void execute(Map aCtx)
	{
		JavaNode node = (JavaNode) EvaluationContextView.getNode(aCtx);
		System.out.println("@@@ JAVA CODE: '" + node.getValue() + "'");
	}
}
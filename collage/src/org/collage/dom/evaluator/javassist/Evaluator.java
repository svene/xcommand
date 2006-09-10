package org.collage.dom.evaluator.javassist;

import org.collage.dom.ast.RootNode;
import org.collage.dom.ast.TextNode;
import org.collage.dom.ast.VariableNode;
import org.collage.dom.ast.JavaNode;
import org.collage.dom.evaluator.NodeVisitor;

import java.util.HashMap;
import java.util.Map;

public class Evaluator extends NodeVisitor
{
	public Evaluator()
	{
		setConfigContext(javassistDomEvaluatorConfigCtx);
	}


// --- Implementation ---

	private static Map javassistDomEvaluatorConfigCtx = new HashMap();

	static
	{
		javassistDomEvaluatorConfigCtx.put(RootNode.class, new RootNodeEvaluator());
		javassistDomEvaluatorConfigCtx.put(TextNode.class, new TextNodeEvaluator());
		javassistDomEvaluatorConfigCtx.put(VariableNode.class, new VariableNodeEvaluator());
		javassistDomEvaluatorConfigCtx.put(JavaNode.class, new JavaNodeEvaluator());
	}

}

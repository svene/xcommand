package org.collage.dom.evaluator.text;

import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.ast.TextNode;
import org.collage.dom.ast.VariableNode;
import org.collage.dom.ast.RootNode;
import org.collage.dom.ast.JavaNode;

import java.util.Map;
import java.util.HashMap;

public class Evaluator extends NodeVisitor
{
	public Evaluator()
	{
		setConfigContext(configCtx);
	}

// --- Implementation ---

	private static Map configCtx = new HashMap();

	static
	{
		configCtx.put(RootNode.class, new RootNodeEvaluator());
		configCtx.put(TextNode.class, new TextNodeEvaluator());
		configCtx.put(VariableNode.class, new VariableNodeEvaluator());
		configCtx.put(JavaNode.class, new JavaNodeEvaluator());
	}
}
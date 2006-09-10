package org.collage.dom.evaluator.domdumper;

import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.ast.TextNode;
import org.collage.dom.ast.VariableNode;
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
		configCtx.put(TextNode.class, new TextNodeDumper());
		configCtx.put(VariableNode.class, new VariableNodeDumper());
		configCtx.put(JavaNode.class, new JavaNodeDumper());
	}
}

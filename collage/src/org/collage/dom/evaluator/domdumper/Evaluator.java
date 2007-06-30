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
		setConfigContext(new DomDumperElementToVisitorMap().getMap());
	}
}

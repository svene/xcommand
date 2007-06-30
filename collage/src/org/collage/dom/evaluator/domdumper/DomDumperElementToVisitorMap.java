package org.collage.dom.evaluator.domdumper;

import org.xcommand.pattern.visitor.IElementToVisitorMap;
import org.collage.dom.ast.TextNode;
import org.collage.dom.ast.VariableNode;
import org.collage.dom.ast.JavaNode;

import java.util.Map;
import java.util.HashMap;

/**
 * TODO: try to make this an example of the Visitor pattern based on XCommand
 */
public class DomDumperElementToVisitorMap implements IElementToVisitorMap
{
	public Map getMap()
	{
		return elementToVisitorMap;
	}

// --- Implementation ---

	private static Map elementToVisitorMap = new HashMap();

	static
	{
		elementToVisitorMap.put(TextNode.class, new TextNodeDumper());
		elementToVisitorMap.put(VariableNode.class, new VariableNodeDumper());
		elementToVisitorMap.put(JavaNode.class, new JavaNodeDumper());
	}
}

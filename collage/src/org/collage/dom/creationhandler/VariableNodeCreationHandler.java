package org.collage.dom.creationhandler;

import org.collage.dom.ast.Variable;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.io.PrintStream;
import java.util.Map;

public class VariableNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		String s = DomNodeCreationHandlerCV.getValue(aCtx);
		trace(aCtx, "got VARIABLE: '" + s + "'");
		TreeNode node = new TreeNode();
		Variable v = new Variable();
		v.setVariableName(s);
		node.setDomainObject(v);
		tb.addChild(TreeNodeCV.getTreeNode(aCtx), node);
	}

// --- Implementation ---

	private void trace(Map aCtx, String aString)
	{
		PrintStream ps = ParserCV.getTraceStream(aCtx);
		if (ps == null) return;
		ps.println("### " + aString);
	}
	private TreeBuilder tb = new TreeBuilder();
}

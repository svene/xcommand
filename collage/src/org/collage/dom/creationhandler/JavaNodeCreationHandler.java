package org.collage.dom.creationhandler;

import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNodeCV;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNode;
import org.collage.dom.ast.Java;
import org.collage.parser.ParserCV;

import java.util.Map;
import java.io.PrintStream;

public class JavaNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		String s = DomNodeCreationHandlerCV.getValue(aCtx);
		trace(aCtx, "got JAVA CODE: '" + s + "'");
		ITreeNode node = new TreeNode();
		Java java = new Java();
		java.setValue(s);
		node.setDomainObject(java);
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

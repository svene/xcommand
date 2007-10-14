package org.collage.dom.creationhandler;

import org.collage.dom.ast.Text;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNodeCV;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNode;

import java.util.Map;
import java.io.PrintStream;

public class TextNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		String s = DomNodeCreationHandlerCV.getValue(aCtx);
		trace(aCtx, "got TEXT: '" + s + "'");
		ITreeNode node = new TreeNode();
		Text text = new Text();
		text.setValue(s);
		node.setDomainObject(text);
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

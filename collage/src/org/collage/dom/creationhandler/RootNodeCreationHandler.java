package org.collage.dom.creationhandler;

import org.collage.dom.ast.RootNode;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.io.PrintStream;
import java.util.Map;

public class RootNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		trace(aCtx, "started");
		TreeNodeCV.setTreeNode(aCtx, new RootNode());
	}

// --- Implementation ---

	private void trace(Map aCtx, String aString)
	{
		PrintStream ps = ParserCV.getTraceStream(aCtx);
		if (ps == null) return;
		ps.println("### " + aString);
	}
}

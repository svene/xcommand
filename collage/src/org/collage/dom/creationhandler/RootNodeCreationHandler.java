package org.collage.dom.creationhandler;

import org.collage.dom.DomContextView;
import org.collage.dom.ast.RootNode;
import org.collage.parser.ParserContextView;
import org.xcommand.core.IXCommand;

import java.io.PrintStream;
import java.util.Map;

public class RootNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		trace(aCtx, "started");
		DomContextView.setRootNode(aCtx, new RootNode());
	}

// --- Implementation ---

	private void trace(Map aCtx, String aString)
	{
		PrintStream ps = ParserContextView.getTraceStream(aCtx);
		if (ps == null) return;
		ps.println("### " + aString);
	}
}

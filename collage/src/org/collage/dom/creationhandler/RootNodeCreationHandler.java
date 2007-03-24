package org.collage.dom.creationhandler;

import org.collage.dom.DomCV;
import org.collage.dom.ast.RootNode;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;

import java.io.PrintStream;
import java.util.Map;

public class RootNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		trace(aCtx, "started");
		DomCV.setRootNode(aCtx, new RootNode());
	}

// --- Implementation ---

	private void trace(Map aCtx, String aString)
	{
		PrintStream ps = ParserCV.getTraceStream(aCtx);
		if (ps == null) return;
		ps.println("### " + aString);
	}
}

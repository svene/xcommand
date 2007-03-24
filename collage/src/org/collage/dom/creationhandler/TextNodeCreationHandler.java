package org.collage.dom.creationhandler;

import org.collage.dom.ast.TextNode;
import org.collage.dom.DomCV;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;

import java.util.Map;
import java.io.PrintStream;

public class TextNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		String s = DomNodeCreationHandlerCV.getValue(aCtx);
		trace(aCtx, "got TEXT: '" + s + "'");
		TextNode node = new TextNode();
		node.setValue(s);
		DomCV.getRootNode(aCtx).getChildren().add(node);
	}

// --- Implementation ---

	private void trace(Map aCtx, String aString)
	{
		PrintStream ps = ParserCV.getTraceStream(aCtx);
		if (ps == null) return;
		ps.println("### " + aString);
	}
}

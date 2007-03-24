package org.collage.dom.creationhandler;

import org.xcommand.core.IXCommand;
import org.collage.dom.ast.JavaNode;
import org.collage.dom.DomCV;
import org.collage.parser.ParserCV;

import java.util.Map;
import java.io.PrintStream;

public class JavaNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		String s = DomNodeCreationHandlerCV.getValue(aCtx);
		trace(aCtx, "got JAVA CODE: '" + s + "'");
		JavaNode node = new JavaNode();
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

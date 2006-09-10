package org.collage.dom.creationhandler;

import org.collage.dom.DomContextView;
import org.collage.dom.ast.VariableNode;
import org.collage.parser.ParserContextView;
import org.xcommand.core.IXCommand;

import java.io.PrintStream;
import java.util.Map;

public class VariableNodeCreationHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		String s = DomNodeCreationHandlerContextView.getValue(aCtx);
		trace(aCtx, "got VARIABLE: '" + s + "'");
		VariableNode node = new VariableNode();
		node.setVariableName(s);
		DomContextView.getRootNode(aCtx).getChildren().add(node);
	}

// --- Implementation ---

	private void trace(Map aCtx, String aString)
	{
		PrintStream ps = ParserContextView.getTraceStream(aCtx);
		if (ps == null) return;
		ps.println("### " + aString);
	}
}

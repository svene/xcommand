package org.collage.dom.creationhandler;

import org.collage.dom.ast.RootNode;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

import java.io.PrintStream;

public class RootNodeCreationHandler implements ICommand
{
	public void execute()
	{
		trace("started");
		treeNodeCV.setTreeNode(new RootNode());
	}

// --- Implementation ---

	private void trace(String aString)
	{
		PrintStream ps = parserCV.getTraceStream();
		if (ps == null) {
			return;
		}
		ps.println("### " + aString);
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

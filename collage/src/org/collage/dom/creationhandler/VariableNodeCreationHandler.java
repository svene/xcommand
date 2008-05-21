package org.collage.dom.creationhandler;

import org.collage.dom.ast.Variable;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

import java.io.PrintStream;

public class VariableNodeCreationHandler implements ICommand
{
	public void execute()
	{
		String s = domNodeCreationHandlerCV.getValue();
		trace("got VARIABLE: '" + s + "'");
		TreeNode node = new TreeNode();
		Variable v = new Variable();
		v.setVariableName(s);
		node.setDomainObject(v);
		tb.addChild(treeNodeCV.getTreeNode(), node);
	}

// --- Implementation ---

	private void trace(String aString)
	{
		PrintStream ps = parserCV.getTraceStream();
		if (ps == null) return;
		ps.println("### " + aString);
	}
	private TreeBuilder tb = new TreeBuilder();
	private DynaBeanProvider dbp = new DynaBeanProvider();
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.getBeanForInterface(ITreeNodeCV.class);
	IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.getBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}

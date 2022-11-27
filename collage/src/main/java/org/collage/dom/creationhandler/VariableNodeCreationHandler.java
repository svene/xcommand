package org.collage.dom.creationhandler;

import org.collage.dom.ast.Variable;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

import java.io.PrintStream;

public class VariableNodeCreationHandler implements ICommand {
	@Override
	public void execute() {
		String s = domNodeCreationHandlerCV.getValue();
		trace("got VARIABLE: '" + s + "'");
		Variable v = new Variable();
		v.setVariableName(s);
		TreeNode node = new TreeNode(v);
		tb.addChild(treeNodeCV.getTreeNode(), node);
	}

	private void trace(String aString) {
		PrintStream ps = parserCV.getTraceStream();
		if (ps == null) {
			return;
		}
		ps.println("### " + aString);
	}

	private final TreeBuilder tb = new TreeBuilder();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}

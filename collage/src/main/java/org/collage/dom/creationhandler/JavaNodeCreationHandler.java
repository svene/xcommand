package org.collage.dom.creationhandler;

import org.collage.dom.ast.Java;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.*;
import org.xcommand.template.parser.IParserCV;

import java.io.PrintStream;

public class JavaNodeCreationHandler implements ICommand
{
	public void execute()
	{
		String s = domNodeCreationHandlerCV.getValue();
		trace("got JAVA CODE: '" + s + "'");
		ITreeNode node = new TreeNode();
		Java java = new Java();
		java.setValue(s);
		node.setDomainObject(java);
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}

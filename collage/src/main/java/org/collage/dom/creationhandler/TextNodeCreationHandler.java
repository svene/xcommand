package org.collage.dom.creationhandler;

import org.collage.dom.ast.Text;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.*;
import org.xcommand.template.parser.IParserCV;

import java.io.PrintStream;

public class TextNodeCreationHandler implements ICommand
{
	public void execute()
	{
		String s = domNodeCreationHandlerCV.getValue();
		trace("got TEXT: '" + s + "'");
		ITreeNode node = new TreeNode();
		Text text = new Text();
		text.setValue(s);
		node.setDomainObject(text);
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
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
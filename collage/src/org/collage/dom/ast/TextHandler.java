package org.collage.dom.ast;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class TextHandler implements ICommand
{
	public void execute()
	{
		ITreeNode node = treeNodeCV.getTreeNode();
		Text text = (Text) node.getDomainObject();
		textCV.setText(text);
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.getBeanForInterface(ITreeNodeCV.class);
	ITextCV textCV = (ITextCV) dbp.getBeanForInterface(ITextCV.class);
}

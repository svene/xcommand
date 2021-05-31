package org.collage.dom.ast;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	ITextCV textCV = dbp.newBeanForInterface(ITextCV.class);
}

package org.collage.dom.ast;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class TextHandler implements ICommand {
	@Override
	public void execute() {
		ITreeNode node = treeNodeCV.getTreeNode();
		Text text = (Text) node.getDomainObject();
		textCV.setText(text);
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	ITextCV textCV = dbp.newBeanForInterface(ITextCV.class);
}

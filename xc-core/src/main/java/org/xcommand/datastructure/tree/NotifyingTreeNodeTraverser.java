package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

/**
 * TreeNodeTraverser sending out notifications while traversing the tree
 */
public class NotifyingTreeNodeTraverser implements ICommand {

	public INotifier getEnterNodeNotifier() {
		return enterNodeNotifier;
	}

	public INotifier getExitNodeNotifier() {
		return exitNodeNotifier;
	}

	@Override
	public void execute() {
		ITreeNode node = treeNodeCV.getTreeNode();
		traverse(node);
	}

	private void traverse(ITreeNode aNode) {
		treeNodeCV.setTreeNode(aNode);
		treeNodeCV.setDomainObject(aNode.getDomainObject());
		enterNodeNotifier.execute();
		aNode.getChildren().forEach(this::traverse);
		treeNodeCV.setTreeNode(aNode);
		treeNodeCV.setDomainObject(aNode.getDomainObject());
		exitNodeNotifier.execute();
	}

	private final INotifier enterNodeNotifier = new BasicNotifier();
	private final INotifier exitNodeNotifier = new BasicNotifier();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
}

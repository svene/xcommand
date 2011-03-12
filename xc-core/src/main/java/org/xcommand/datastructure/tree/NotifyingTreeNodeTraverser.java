package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

/**
 * TreeNodeTraverser sending out notifications while traversing the tree
 */
public class NotifyingTreeNodeTraverser implements ICommand
{

// --- Access ---

	public INotifier getEnterNodeNotifier()
	{
		return enterNodeNotifier;
	}

	public INotifier getExitNodeNotifier()
	{
		return exitNodeNotifier;
	}

// --- Processing ---

	@Override
	public void execute()
	{
		ITreeNode node = treeNodeCV.getTreeNode();
		traverse(node);
	}

	private void traverse(ITreeNode aNode)
	{
		treeNodeCV.setTreeNode(aNode);
		treeNodeCV.setDomainObject(aNode.getDomainObject());
		enterNodeNotifier.execute();
		for (ITreeNode node : aNode.getChildren()) {
			traverse(node);
		}
		treeNodeCV.setTreeNode(aNode);
		treeNodeCV.setDomainObject(aNode.getDomainObject());
		exitNodeNotifier.execute();
	}

// --- Implementation ---

	private INotifier enterNodeNotifier = new BasicNotifier();
	private INotifier exitNodeNotifier = new BasicNotifier();
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
}

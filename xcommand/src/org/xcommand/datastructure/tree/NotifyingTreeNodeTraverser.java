package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.ISubject;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.Iterator;
import java.util.Map;

/**
 * TreeNodeTraverser sending out notifications while traversing the tree
 */
public class NotifyingTreeNodeTraverser implements IXCommand
{

// --- Access ---

	public ISubject getEnterNodeNotifier()
	{
		return enterNodeNotifier;
	}

	public ISubject getExitNodeNotifier()
	{
		return exitNodeNotifier;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		ITreeNode node = TreeNodeCV.getTreeNode(aCtx);
		traverse(aCtx, node);
	}

	private void traverse(Map aCtx, ITreeNode aNode)
	{
		TreeNodeCV.setTreeNode(aCtx, aNode);
		TreeNodeCV.setDomainObject(aCtx, aNode.getDomainObject());
		enterNodeNotifier.execute(aCtx);
		Iterator it = aNode.getChildren().iterator();
		while (it.hasNext())
		{
			ITreeNode node = (ITreeNode) it.next();
			traverse(aCtx, node);
		}
		TreeNodeCV.setTreeNode(aCtx, aNode);
		TreeNodeCV.setDomainObject(aCtx, aNode.getDomainObject());
		exitNodeNotifier.execute(aCtx);
	}

// --- Implementation ---

	private ISubject enterNodeNotifier = new SubjectImpl();
	private ISubject exitNodeNotifier = new SubjectImpl();

}

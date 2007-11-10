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

	public ISubject getEnterNodeEvent()
	{
		return enterNodeEvent;
	}

	public ISubject getExitNodeEvent()
	{
		return exitNodeEvent;
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
		notifyAboutNodeEntry(aCtx);
		Iterator it = aNode.getChildren().iterator();
		while (it.hasNext())
		{
			ITreeNode node = (ITreeNode) it.next();
			traverse(aCtx, node);
		}
		TreeNodeCV.setTreeNode(aCtx, aNode);
		TreeNodeCV.setDomainObject(aCtx, aNode.getDomainObject());
		notifyAboutNodeExit(aCtx);
	}

// --- Implementation ---

	private void notifyAboutNodeEntry(Map aCtx)
	{
		enterNodeEvent.execute(aCtx);
	}
	private void notifyAboutNodeExit(Map aCtx)
	{
		exitNodeEvent.execute(aCtx);
	}

	private ISubject enterNodeEvent = new SubjectImpl();
	private ISubject exitNodeEvent = new SubjectImpl();

}

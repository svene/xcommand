package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

import java.util.Map;

/**
 * IXCommand traversing over a tree structure and while doing this invokes
 * pre-, exec-,  and posthandlers for each TreeNode.
 * This makes it possible to invoke commands before and after the real handling of
 * the TreeNode. 
 */
public class TreeNodeHandlingTraverser implements IXCommand
{
	public void execute(Map aCtx)
	{
		TreeNode root = (TreeNode) aCtx.get("root");
		TreeNodeIterator it = new TreeNodeIterator(root);
		while (it.hasNext())
		{
			TreeNode te = (TreeNode) it.next();
			TreeNodeCV.setTreeNode(aCtx, te);
			executeHandlerForTreeElement(preHandlerProvider, aCtx);
			executeHandlerForTreeElement(handlerProvider, aCtx);
			executeHandlerForTreeElement(postHandlerProvider, aCtx);
		}
	}

	private void executeHandlerForTreeElement(IHandlerProvider aHandlerProvider, Map aCtx)
	{
		if (aHandlerProvider == null) return;
		IXCommand hdl = aHandlerProvider.getHandler(TreeNodeCV.getTreeNode(aCtx));
		if (hdl == null) return;
		hdl.execute(aCtx);
	}

// --- Setting ---

	public void setPreHandlerProvider(IHandlerProvider aPreHandlerProvider)
	{
		preHandlerProvider = aPreHandlerProvider;
	}

	public void setHandlerProvider(IHandlerProvider aHandlerProvider)
	{
		handlerProvider = aHandlerProvider;
	}

	public void setPostHandlerProvider(IHandlerProvider aPostHandlerProvider)
	{
		postHandlerProvider = aPostHandlerProvider;
	}

// --- Implementation ---

	IHandlerProvider preHandlerProvider;
	IHandlerProvider handlerProvider;
	IHandlerProvider postHandlerProvider;
}

package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

import java.util.Map;

/**
 * IXCommand acting as a Dispatcher by using an HandlerProvider to determine
 * the real Command to execute using `TreeNodeCV.getTreeNode()' as the lookup key
 * for the HandlerProvider. 
 */
class HandlerProviderBasedCommand implements IXCommand
{

// --- Initialization ---

	public HandlerProviderBasedCommand(IHandlerKeyProvider aHandlerKeyProvider, IHandlerProvider aHandlerProvider)
	{
		handlerKeyProvider = aHandlerKeyProvider;
		handlerProvider = aHandlerProvider;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		ITreeNode tn = TreeNodeCV.getTreeNode(aCtx);
		Object key = tn;
		if (handlerKeyProvider != null)
		{
			key = handlerKeyProvider.getHandlerKey(tn);
		}
		IXCommand cmd = handlerProvider.getHandler(key);
		if (cmd != null)
		{
			cmd.execute(aCtx);
		}
	}

// --- Implementation ---

	private IHandlerKeyProvider handlerKeyProvider = null;
	private IHandlerProvider handlerProvider;

}

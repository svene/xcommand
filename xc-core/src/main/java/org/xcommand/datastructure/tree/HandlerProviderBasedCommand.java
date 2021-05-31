package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

/**
 * ICommand acting as a Dispatcher by using an HandlerProvider to determine
 * the real Command to execute using `TreeNodeCV.getTreeNode()' as the lookup key
 * for the HandlerProvider. 
 */
class HandlerProviderBasedCommand implements ICommand
{

// --- Initialization ---

	HandlerProviderBasedCommand(IHandlerKeyProvider aHandlerKeyProvider, IHandlerProvider aHandlerProvider)
	{
		handlerKeyProvider = aHandlerKeyProvider;
		handlerProvider = aHandlerProvider;
	}

// --- Processing ---

	public void execute()
	{
		ITreeNode tn = treeNodeCV.getTreeNode();
		Object key = handlerKeyProvider == null ? tn : handlerKeyProvider.getHandlerKey(tn);
		ICommand cmd = handlerProvider.getHandler(key);
		if (cmd != null)
		{
			cmd.execute();
		}
	}

// --- Implementation ---

	private IHandlerKeyProvider handlerKeyProvider;
	private IHandlerProvider handlerProvider;
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);

}

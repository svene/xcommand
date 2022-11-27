package org.xcommand.datastructure.tree;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.handlerprovider.IHandlerKeyProvider;
import org.xcommand.datastructure.handlerprovider.IHandlerProvider;

/**
 * ICommand acting as a Dispatcher by using an HandlerProvider to determine
 * the real Command to execute using `TreeNodeCV.getTreeNode()' as the lookup key
 * for the HandlerProvider.
 */
class HandlerProviderBasedCommand implements ICommand {

	HandlerProviderBasedCommand(IHandlerKeyProvider aHandlerKeyProvider, IHandlerProvider aHandlerProvider) {
		handlerKeyProvider = aHandlerKeyProvider;
		handlerProvider = aHandlerProvider;
	}

	@Override
	public void execute() {
		ITreeNode tn = treeNodeCV.getTreeNode();
		Object key = handlerKeyProvider == null ? tn : handlerKeyProvider.getHandlerKey(tn);
		ICommand cmd = handlerProvider.getHandler(key);
		if (cmd != null) {
			cmd.execute();
		}
	}

	private final IHandlerKeyProvider handlerKeyProvider;
	private final IHandlerProvider handlerProvider;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);

}

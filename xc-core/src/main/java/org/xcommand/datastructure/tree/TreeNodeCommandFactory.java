package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;
import org.xcommand.datastructure.handlerprovider.IHandlerKeyProvider;
import org.xcommand.datastructure.handlerprovider.IHandlerProvider;
import org.xcommand.util.ClassAdapter;
import org.xcommand.util.IdentityObjectAdapter;
import org.xcommand.util.NestableObjectAdapter;

public final class TreeNodeCommandFactory {
	private TreeNodeCommandFactory() {
	}

	public static ICommand newTreeNodeKeyedCommand(IHandlerProvider aHandlerProvider) {
		return newHandlerProviderBasedCommand(aHandlerProvider, new TreeNodeAdapter(new IdentityObjectAdapter()));
	}

	public static ICommand newTreeNodeDomainObjectKeyedCommand(IHandlerProvider aHandlerProvider) {
		return newHandlerProviderBasedCommand(aHandlerProvider, new TreeNodeDomainObjectAdapter(new IdentityObjectAdapter()));
	}

	private static ICommand newHandlerProviderBasedCommand(IHandlerProvider aHandlerProvider, NestableObjectAdapter aObjectAdapter) {
		IHandlerKeyProvider handlerKeyProvider = new ClassAdapter(aObjectAdapter)::adaptedObject;
		return new HandlerProviderBasedCommand(handlerKeyProvider, aHandlerProvider);
	}

}

package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;
import org.xcommand.datastructure.handlerprovider.IHandlerKeyProvider;
import org.xcommand.datastructure.handlerprovider.IHandlerProvider;
import org.xcommand.util.ClassAdapter;
import org.xcommand.util.NestableObjectAdapter;

public class TreeNodeCommandFactory
{
	private TreeNodeCommandFactory() {
	}

	public static ICommand newTreeNodeKeyedCommand(IHandlerProvider aHandlerProvider)
	{
		return newHandlerProviderBasedCommand(aHandlerProvider, new TreeNodeAdapter(null));
	}

	public static ICommand newTreeNodeDomainObjectKeyedCommand(IHandlerProvider aHandlerProvider)
	{
		return newHandlerProviderBasedCommand(aHandlerProvider, new TreeNodeDomainObjectAdapter(null));
	}

	private static ICommand newHandlerProviderBasedCommand(IHandlerProvider aHandlerProvider, NestableObjectAdapter aObjectAdapter) {
		IHandlerKeyProvider handlerKeyProvider = (obj) -> new ClassAdapter(aObjectAdapter).adaptedObject(obj);
		return new HandlerProviderBasedCommand(handlerKeyProvider, aHandlerProvider);
	}

}

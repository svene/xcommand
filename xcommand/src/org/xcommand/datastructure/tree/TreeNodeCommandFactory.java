package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;
import org.xcommand.util.ClassAdapter;

public class TreeNodeCommandFactory
{
	public static ICommand newTreeNodeKeyedCommand(IHandlerProvider aHandlerProvider)
	{
		IHandlerKeyProvider hkp = new AdapterBasedHandlerKeyProvider(new ClassAdapter(new TreeNodeAdapter(null)));
		return new HandlerProviderBasedCommand(hkp, aHandlerProvider);
	}

	public static ICommand newTreeNodeDomainObjectKeyedCommand(IHandlerProvider aHandlerProvider)
	{
		IHandlerKeyProvider hkp = new AdapterBasedHandlerKeyProvider(new ClassAdapter(new TreeNodeDomainObjectAdapter(null)));
		return new HandlerProviderBasedCommand(hkp, aHandlerProvider);
	}

}

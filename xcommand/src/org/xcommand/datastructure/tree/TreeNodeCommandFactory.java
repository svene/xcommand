package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;
import org.xcommand.util.ClassAdapter;

public class TreeNodeCommandFactory
{
	public static IXCommand newTreeNodeKeyedCommand(IHandlerProvider aHandlerProvider)
	{
		IHandlerKeyProvider hkp = new AdapterBasedHandlerKeyProvider(new ClassAdapter(new TreeNodeAdapter(null)));
		return new HandlerProviderBasedCommand(hkp, aHandlerProvider);
	}

	public static IXCommand newTreeNodeDomainObjectKeyedCommand(IHandlerProvider aHandlerProvider)
	{
		IHandlerKeyProvider hkp = new AdapterBasedHandlerKeyProvider(new ClassAdapter(new TreeNodeDomainObjectAdapter(null)));
		return new HandlerProviderBasedCommand(hkp, aHandlerProvider);
	}

}

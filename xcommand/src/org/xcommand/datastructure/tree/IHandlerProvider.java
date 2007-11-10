package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

/**
 * Provider for handlers based on an object
 */
public interface IHandlerProvider
{
	public IXCommand getHandler(Object aObj);
}

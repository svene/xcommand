package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

/**
 * Provider for handlers based on an object
 */
public interface IHandlerProvider
{
	/** get key to lookup handler via `aObj' */
	public Object getHandlerKey(Object aObj);
	public IXCommand getHandler(Object aObj);
}

package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;

/**
 * Provider for handlers based on an object
 */
public interface IHandlerProvider
{
	ICommand getHandler(Object aObj);
}

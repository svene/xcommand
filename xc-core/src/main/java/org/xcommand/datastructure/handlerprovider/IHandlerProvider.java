package org.xcommand.datastructure.handlerprovider;

import org.xcommand.core.ICommand;

/**
 * Provider for handlers based on an object
 */
@FunctionalInterface
public interface IHandlerProvider {
	ICommand getHandler(Object aObj);
}

package org.xcommand.datastructure.handlerprovider;

import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

/**
 * Provider for handlers based on an object
 */
@FunctionalInterface
public interface IHandlerProvider<K, T extends ICommand> {
    @Nullable
    T getHandler(K aObj);
}

package org.xcommand.datastructure.handlerprovider;

import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.ICommand;

/**
 * `IHandlerProvider' using a 'java.util.Map' to lookup a handler
 */
public class MapBasedHandlerProvider<K, T extends ICommand> implements IHandlerProvider<K, T> {

    private final Map<K, T> handlerMap;

    public MapBasedHandlerProvider(Map<K, T> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    @Nullable
    public T getHandler(K aObj) {
        return handlerMap.get(aObj);
    }
}

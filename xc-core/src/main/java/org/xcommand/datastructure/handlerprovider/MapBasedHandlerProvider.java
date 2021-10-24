package org.xcommand.datastructure.handlerprovider;

import org.xcommand.core.ICommand;

import java.util.Map;

/**
 * `IHandlerProvider' using a 'java.util.Map' to lookup a handler
 */
public class MapBasedHandlerProvider implements IHandlerProvider
{

	private final Map<Object, ? extends ICommand> handlerMap;

	public MapBasedHandlerProvider(Map<Object, ? extends ICommand> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	public ICommand getHandler(Object aObj)
	{
		return handlerMap.get(aObj);
	}

}

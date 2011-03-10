package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;

import java.util.Map;

/**
 * `IHandlerProvider' using a 'java.util.Map' to lookup a handler
 */
public class MapBasedHandlerProvider implements IHandlerProvider
{

// --- Access ---

	public Map<Object, ? extends ICommand> getHandlerMap()
	{
		return handlerMap;
	}

	@Override
	public ICommand getHandler(Object aObj)
	{
		return handlerMap.get(aObj);
	}

// --- Setting ---

	public void setHandlerMap(Map<Object, ? extends ICommand> aHandlerMap)
	{
		handlerMap = aHandlerMap;
	}

// --- Implementation ---

	private Map<Object, ? extends ICommand> handlerMap;

}

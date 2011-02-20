package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;

import java.util.Map;

/**
 * `IHandlerProvider' using a handlerMap to lookup a handler 
 */
public class MapBasedHandlerProvider implements IHandlerProvider
{

// --- Access ---

	public Map<Object, ICommand> getHandlerMap()
	{
		return handlerMap;
	}

	public ICommand getHandler(Object aObj)
	{
		return handlerMap.get(aObj);
	}

// --- Setting ---

	public void setHandlerMap(Map<Object, ICommand> aHandlerMap)
	{
		handlerMap = aHandlerMap;
	}

// --- Implementation ---

	private Map<Object, ICommand> handlerMap;

}

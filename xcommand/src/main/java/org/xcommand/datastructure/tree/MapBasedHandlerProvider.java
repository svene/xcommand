package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;

import java.util.Map;

/**
 * `IHandlerProvider' using a handlerMap to lookup a handler 
 */
public abstract class MapBasedHandlerProvider implements IHandlerProvider
{

// --- Access ---

	public Map getHandlerMap()
	{
		return handlerMap;
	}

	public ICommand getHandler(Object aObj)
	{
		return (ICommand) handlerMap.get(aObj);
	}

// --- Setting ---

	public void setHandlerMap(Map aHandlerMap)
	{
		handlerMap = aHandlerMap;
	}

// --- Implementation ---

	private Map/*<Object, IComand>*/ handlerMap;

}

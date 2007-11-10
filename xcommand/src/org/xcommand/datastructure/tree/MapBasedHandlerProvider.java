package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

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

	public IXCommand getHandler(Object aObj)
	{
		return (IXCommand) handlerMap.get(aObj);
	}

// --- Setting ---

	public void setHandlerMap(Map aHandlerMap)
	{
		handlerMap = aHandlerMap;
	}

// --- Implementation ---

	private Map/*<Object, IXComand>*/ handlerMap;

}

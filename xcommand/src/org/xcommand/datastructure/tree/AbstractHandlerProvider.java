package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

import java.util.Map;

public abstract class AbstractHandlerProvider implements IHandlerProvider
{
// --- Access ---


	/**
	 * Default handlerKey: `aObj'
	 */
	public Object getHandlerKey(Object aObj)
	{
		return aObj;
	}


	/**
	 * Default behavior: return handler in `getHandlerMap()' under key
	 * `getHandlerKey(aObj)'
	 */
	public IXCommand getHandler(Object aObj)
	{
		Object key = getHandlerKey(aObj);
		return (IXCommand) getHandlerMap().get(key);
	}

	public Map getHandlerMap()
	{
		return handlerMap;
	}

// --- Setting ---

	public void setHandlerMap(Map aHandlerMap)
	{
		handlerMap = aHandlerMap;
	}

// --- Implementation ---

	private Map/*<Class, IXComand>*/ handlerMap;
}

package org.xcommand.datastructure.tree;

/**
 * `IHandlerProvider' which uses the objects class to lookup the associated handler
 */
public class ClassBasedHandlerProvider extends AbstractHandlerProvider
{
	public Object getHandlerKey(Object aObj)
	{
		return aObj.getClass();
	}
}

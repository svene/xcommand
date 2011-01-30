package org.xcommand.datastructure.tree;

/**
 * Provider of keys for the lookup handlers. The key is determined
 * based upon an object.
 */
public interface IHandlerKeyProvider
{
	/** get key to lookup handler via `aObj' */
	public Object getHandlerKey(Object aObj);
}

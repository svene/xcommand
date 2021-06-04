package org.xcommand.datastructure.handlerprovider;

/**
 * Provider of keys for the lookup handlers. The key is determined
 * based upon an object.
 */
public interface IHandlerKeyProvider
{
	/** get key to lookup handler via `aObj' */
	Object getHandlerKey(Object aObj);
}

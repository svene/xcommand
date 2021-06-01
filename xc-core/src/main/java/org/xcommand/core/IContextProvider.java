package org.xcommand.core;

import java.util.Map;

@FunctionalInterface
public interface IContextProvider
{
	Map getContext();
}

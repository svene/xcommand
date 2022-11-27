package org.xcommand.core;

@FunctionalInterface
public interface IDynaBeanKeyProvider {
	String getKey(InvocationContext ihc);
}

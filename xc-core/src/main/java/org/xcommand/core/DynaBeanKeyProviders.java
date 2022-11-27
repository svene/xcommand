package org.xcommand.core;

public class DynaBeanKeyProviders {
	private DynaBeanKeyProviders() {
	}

	public static final IDynaBeanKeyProvider ClassAndMethodKeyProvider =
		(InvocationContext ihc) -> ihc.methodInfo().propertyPath();

	public static final IDynaBeanKeyProvider MethodKeyProvider =
		(InvocationContext ihc) -> ihc.methodInfo().property();

	public static final IDynaBeanKeyProvider ObjectIdentityKeyProvider =
		(InvocationContext ihc) ->
			System.identityHashCode(ihc.proxy()) + ihc.methodInfo().property();

}

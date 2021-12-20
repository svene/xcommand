package org.xcommand.core;

public class DynaBeanKeyProviders {
	private DynaBeanKeyProviders() {
	}

	public static IDynaBeanKeyProvider ClassAndMethodKeyProvider =
		(InvocationContext ihc) -> ihc.methodInfo().propertyPath();

	public static IDynaBeanKeyProvider MethodKeyProvider =
		(InvocationContext ihc) -> ihc.methodInfo().property;

	public static IDynaBeanKeyProvider ObjectIdentityKeyProvider =
		(InvocationContext ihc) ->
			System.identityHashCode(ihc.proxy()) + ihc.methodInfo().property;

}

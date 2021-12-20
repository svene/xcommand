package org.xcommand.core;

public class DynaBeanKeyProviders {
	private DynaBeanKeyProviders() {
	}

	public static IDynaBeanKeyProvider ClassAndMethodKeyProvider =
		(InvocationHandlerContext ihc) -> ihc.methodInfo().propertyPath();

	public static IDynaBeanKeyProvider MethodKeyProvider =
		(InvocationHandlerContext ihc) -> ihc.methodInfo().property;

	public static IDynaBeanKeyProvider ObjectIdentityKeyProvider =
		(InvocationHandlerContext ihc) ->
			System.identityHashCode(ihc.proxy()) + ihc.methodInfo().property;

}

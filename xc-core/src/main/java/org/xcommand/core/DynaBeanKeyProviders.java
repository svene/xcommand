package org.xcommand.core;

public class DynaBeanKeyProviders {
	private DynaBeanKeyProviders() {
	}

	public static IDynaBeanKeyProvider ClassAndMethodKeyProvider =
		(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs) ->
			aMethodInfo.methodClassName + "." + aMethodInfo.property;

	public static IDynaBeanKeyProvider MethodKeyProvider =
		(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs) ->
			aMethodInfo.property;

	public static IDynaBeanKeyProvider ObjectIdentityKeyProvider =
		(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs) ->
			System.identityHashCode(aTargetObj) + aMethodInfo.property;

}

package org.xcommand.core;

public class DynaBeanKeyProviders {
	private DynaBeanKeyProviders() {
	}

	public static String classAndMethodKeyProvider(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs) {
		return aMethodInfo.methodClassName + "." + aMethodInfo.property;
	}
	public static String methodKeyProvider(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs) {
		return aMethodInfo.property;
	}
	public static String objectIdentityKeyProvider(Object aTargetObj, MethodInfo aMethodInfo, Object[] aArgs) {
		return System.identityHashCode(aTargetObj) + aMethodInfo.property;
	}
}

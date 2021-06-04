package org.xcommand.core;

public class BeanAccessors {
	private BeanAccessors() {
	}

	public static IBeanAccessor newBeanAccessor(IContextProvider contextProvider, IDynaBeanKeyProvider dynaBeanKeyProvider) {
		return new ContextProviderBasedBeanAccessor(contextProvider, dynaBeanKeyProvider);
	}
}

package org.xcommand.core;

public class BeanAccessors {
	private BeanAccessors() {
	}

	public static IBeanAccessor newThreadClassMethodInstance() {
		return newBeanAccessor(TCP::getContext, new ClassAndMethodKeyProvider());
	}

	public static IBeanAccessor newBeanAccessor(IContextProvider contextProvider, IDynaBeanKeyProvider dynaBeanKeyProvider) {
		return new ContextProviderBasedBeanAccessor(contextProvider, dynaBeanKeyProvider);
	}
}

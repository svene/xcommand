package org.xcommand.core;

import eu.javaspecialists.books.dynamicproxies.Proxies;

import java.lang.reflect.InvocationHandler;

public class DynaBeanProvider
{

	private DynaBeanProvider() {
	}

	private static DynaBeanOptions ThreadClassMethodOptions =
		DynaBeanOptions.builder()
			.contextProvider(TCP::getContext)
			.dynaBeanKeyProvider(DynaBeanKeyProviders.ClassAndMethodKeyProvider)
			.build();
	private static DynaBeanOptions ThreadMethodOptions =
		DynaBeanOptions.builder()
			.contextProvider(TCP::getContext)
			.dynaBeanKeyProvider(DynaBeanKeyProviders.MethodKeyProvider)
			.build();
	private static DynaBeanOptions ThreadObjectIdentityOptions =
		DynaBeanOptions.builder()
			.contextProvider(TCP::getContext)
			.dynaBeanKeyProvider(DynaBeanKeyProviders.ObjectIdentityKeyProvider)
			.build();

	private static IDynaBeanProvider ThreadClassMethodDynaBeanProvider = fromOptions(ThreadClassMethodOptions);
	private static IDynaBeanProvider ThreadMethodDynaBeanProvider = fromOptions(ThreadMethodOptions);
	private static IDynaBeanProvider ThreadObjectIdentityDynaBeanProvider = fromOptions(ThreadObjectIdentityOptions);

	public static IDynaBeanProvider fromOptions(DynaBeanOptions options) {
		return newDynaBeanProvider(newDynaBeanInvocationHandler(newBeanAccessor(options)));
	}

	public static IDynaBeanProvider newThreadClassMethodInstance() {
		return ThreadClassMethodDynaBeanProvider;
	}
	public static IDynaBeanProvider newThreadMethodInstance() {
		return ThreadMethodDynaBeanProvider;
	}
	public static IDynaBeanProvider newThreadObjectIdentityInstance() {
		return ThreadObjectIdentityDynaBeanProvider;
	}

	public static IDynaBeanProvider newDynaBeanProvider(InvocationHandler invocationHandler) {
		return new IDynaBeanProvider() {
			@Override
			public <T> T newBeanForInterface(Class<T> interfaze) {
				return Proxies.castProxy(interfaze, invocationHandler);
			}
		};
	}

	public static IBeanAccessor newBeanAccessor(DynaBeanOptions options) {
		return BeanAccessors.newBeanAccessor(options.getContextProvider(), options.getDynaBeanKeyProvider());
	}

	public static InvocationHandler newDynaBeanInvocationHandler(IBeanAccessor beanAccessor) {
		return new DynaBeanInvocationHandler(new BeanAccessorInvocationContextHandler(beanAccessor));
	}

}

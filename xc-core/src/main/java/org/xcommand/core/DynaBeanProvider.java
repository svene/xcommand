package org.xcommand.core;

import eu.javaspecialists.books.dynamicproxies.Proxies;
import java.lang.reflect.InvocationHandler;

public final class DynaBeanProvider {

    private DynaBeanProvider() {}

    private static final DynaBeanOptions ThreadClassMethodOptions = DynaBeanOptionsBuilder.builder()
            .contextProvider(TCP::getContext)
            .dynaBeanKeyProvider(DynaBeanKeyProviders.ClassAndMethodKeyProvider)
            .build();
    private static final DynaBeanOptions ThreadMethodOptions = DynaBeanOptionsBuilder.builder()
            .contextProvider(TCP::getContext)
            .dynaBeanKeyProvider(DynaBeanKeyProviders.MethodKeyProvider)
            .build();
    private static final DynaBeanOptions ThreadObjectIdentityOptions = DynaBeanOptionsBuilder.builder()
            .contextProvider(TCP::getContext)
            .dynaBeanKeyProvider(DynaBeanKeyProviders.ObjectIdentityKeyProvider)
            .build();

    private static final IDynaBeanProvider ThreadClassMethodDynaBeanProvider = fromOptions(ThreadClassMethodOptions);
    private static final IDynaBeanProvider ThreadMethodDynaBeanProvider = fromOptions(ThreadMethodOptions);
    private static final IDynaBeanProvider ThreadObjectIdentityDynaBeanProvider =
            fromOptions(ThreadObjectIdentityOptions);

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
        return BeanAccessors.newBeanAccessor(options.contextProvider(), options.dynaBeanKeyProvider());
    }

    public static InvocationHandler newDynaBeanInvocationHandler(IBeanAccessor beanAccessor) {
        return new DynaBeanInvocationHandler(new BeanAccessorInvocationContextHandler(beanAccessor));
    }
}

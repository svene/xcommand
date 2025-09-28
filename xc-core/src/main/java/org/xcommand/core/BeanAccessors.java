package org.xcommand.core;

import java.util.Map;
import java.util.function.Supplier;

public final class BeanAccessors {
    private BeanAccessors() {}

    public static IBeanAccessor newBeanAccessor(
            Supplier<Map<String, Object>> contextProvider, IDynaBeanKeyProvider dynaBeanKeyProvider) {
        return new ContextProviderBasedBeanAccessor(contextProvider, dynaBeanKeyProvider);
    }
}

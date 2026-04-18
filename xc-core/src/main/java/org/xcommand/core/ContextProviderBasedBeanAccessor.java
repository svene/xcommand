package org.xcommand.core;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public record ContextProviderBasedBeanAccessor(
        Supplier<Map<String, Object>> contextSupplier, IDynaBeanKeyProvider dynaBeanKeyProvider)
        implements IBeanAccessor {

    @Override
    public void set(InvocationContext ihc) {
        String key = dynaBeanKeyProvider.getKey(ihc);
        Object[] args = ihc.args();
        contextSupplier.get().put(key, args.length == 1 ? args[0] : args);
    }

    @Override
    public Object get(InvocationContext ihc) {
        String key = dynaBeanKeyProvider.getKey(ihc);
        Object value = contextSupplier.get().get(key);
        if (ihc.methodInfo().method().getReturnType() == Optional.class) {
            return Optional.ofNullable(value);
        }
        if (value == null) {
            throw new IllegalStateException("CV value not set in context: " + key);
        }
        return value;
    }
}

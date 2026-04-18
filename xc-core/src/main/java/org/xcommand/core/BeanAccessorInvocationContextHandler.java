package org.xcommand.core;

import org.jspecify.annotations.Nullable;

/**
 * Adapter from invoke call to get/set call
 */
public record BeanAccessorInvocationContextHandler(IBeanAccessor beanAccessor) implements InvocationContextHandler {

    @Override
    @Nullable
    public Object invoke(InvocationContext ihc) {
        return switch (ihc.methodInfo()) {
            case MethodInfo.Setter s -> {
                beanAccessor.set(ihc);
                yield null;
            }
            case MethodInfo.Has h -> beanAccessor.has(ihc);
            case MethodInfo.Getter g -> beanAccessor.get(ihc);
        };
    }
}

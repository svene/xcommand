package org.xcommand.core;

import org.jspecify.annotations.Nullable;

public interface IBeanAccessor {
    void set(InvocationContext ihc);

    @Nullable
    Object get(InvocationContext ihc);
}

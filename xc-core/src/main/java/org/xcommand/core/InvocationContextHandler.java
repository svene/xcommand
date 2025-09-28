package org.xcommand.core;

import org.jspecify.annotations.Nullable;

public interface InvocationContextHandler {
    @Nullable
    Object invoke(InvocationContext ihc);
}

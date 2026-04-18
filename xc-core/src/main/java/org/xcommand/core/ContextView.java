package org.xcommand.core;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContextView {

    private static final Logger log = LoggerFactory.getLogger(ContextView.class);

    public ContextView() {
        register();
    }

    protected void register() {
        String className = getClass().getName();
        log.debug("className = {}", className);
        TCP.getContext().put(className, this);
    }

    @Nullable
    public static <T extends ContextView> T getContextView(Class<T> aClass) {
        return aClass.cast(TCP.getContext().get(aClass.getName()));
    }

    public static void removeContextView(Class<?> aClass) {
        TCP.getContext().remove(aClass.getName());
    }
}

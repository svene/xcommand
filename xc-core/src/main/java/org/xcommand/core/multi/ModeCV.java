package org.xcommand.core.multi;

import org.jspecify.annotations.Nullable;
import org.xcommand.core.TCP;

public final class ModeCV {
    private ModeCV() {}

    @Nullable
    public static String getMode() {
        return getMode(KEY_MODE);
    }

    @Nullable
    public static String getNewMode() {
        return getMode(KEY_NEW_MODE);
    }

    @Nullable
    public static String getMode(String key) {
        return switch (TCP.getContext().get(key)) {
            case String s -> s;
            case null, default -> null;
        };
    }

    public static void setMode(String aMode) {
        TCP.getContext().put(KEY_MODE, aMode);
    }

    public static void setNewMode(String aMode) {
        TCP.getContext().put(KEY_NEW_MODE, aMode);
    }

    public static final String NS = ModeCV.class.getName() + ".";
    public static final String KEY_MODE = NS + "mode";
    public static final String KEY_NEW_MODE = NS + "new_mode";
}

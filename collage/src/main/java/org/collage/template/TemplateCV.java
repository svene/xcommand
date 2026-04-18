package org.collage.template;

import java.io.Writer;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.TCP;

/**
 * Context View with UserFriendly property 'writer' (see also comment in 'ITemplateCV')
 */
public final class TemplateCV {
    private TemplateCV() {}

    @Nullable
    public static Writer getWriter() {
        var obj = TCP.getContext().get(KEY_UF_WRITER);
        return obj instanceof Writer w ? w : null;
    }

    public static void setWriter(Writer aWriter) {
        TCP.getContext().put(KEY_UF_WRITER, aWriter);
    }

    @SuppressWarnings("unused")
    private static final String NS = "org.collage.template.TemplateCV.";
    /* UF : user friendly */
    private static final String KEY_UF_WRITER = "writer";
}

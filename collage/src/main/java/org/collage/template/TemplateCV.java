package org.collage.template;

import java.io.Writer;
import org.xcommand.core.TCP;

/**
 * Context View with UserFriendly property 'writer' (see also comment in 'ITemplateCV')
 */
public final class TemplateCV {
    private TemplateCV() {}

    public static Writer getWriter() {
        return (Writer) TCP.getContext().get(KEY_UF_WRITER);
    }

    public static void setWriter(Writer aWriter) {
        TCP.getContext().put(KEY_UF_WRITER, aWriter);
    }

    @SuppressWarnings("unused")
    private static final String NS = "org.collage.template.TemplateCV.";
    /* UF : user friendly */
    private static final String KEY_UF_WRITER = "writer";
}

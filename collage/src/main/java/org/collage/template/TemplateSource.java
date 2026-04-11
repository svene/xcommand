package org.collage.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.xcommand.core.TCP;

public record TemplateSource(InputStream inputStream) {

    public TemplateSource {
        if (inputStream == null) {
            throw new RuntimeException("aTemplateStream == null");
        }
    }

    public TemplateSource(String aTemplateText) {
        this(new ByteArrayInputStream(aTemplateText.getBytes(StandardCharsets.UTF_8)));
    }

    public Map<String, Object> getContext() {
        return TCP.getContext();
    }
}

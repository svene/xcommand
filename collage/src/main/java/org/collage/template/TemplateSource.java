package org.collage.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.xcommand.core.TCP;

public class TemplateSource {

    public TemplateSource(InputStream aTemplateStream) {
        if (aTemplateStream == null) {
            throw new RuntimeException("aTemplateStream == null");
        }

        inputStream = aTemplateStream;
    }

    public TemplateSource(String aTemplateText) {
        this(new ByteArrayInputStream(aTemplateText.getBytes(StandardCharsets.UTF_8)));
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Map<String, Object> getContext() {
        return TCP.getContext();
    }

    public void setInputStream(InputStream aInputStream) {
        inputStream = aInputStream;
    }

    private InputStream inputStream;
}

package org.xcommand.template.jst;

import io.soabase.recordbuilder.core.RecordBuilder;
import java.nio.file.Path;

@RecordBuilder
public record FileMapEntry(String key, Path path, long lastmodified, String content, Path rootPath)
        implements FileMapEntryBuilder.With {}

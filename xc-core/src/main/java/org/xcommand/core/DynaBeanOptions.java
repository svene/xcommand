package org.xcommand.core;

import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.Map;
import java.util.function.Supplier;

@RecordBuilder
public record DynaBeanOptions(IDynaBeanKeyProvider dynaBeanKeyProvider, Supplier<Map<String, Object>> contextProvider)
        implements DynaBeanOptionsBuilder.With {}

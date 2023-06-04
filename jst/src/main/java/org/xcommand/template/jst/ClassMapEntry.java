package org.xcommand.template.jst;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record ClassMapEntry(
	FileMapEntry fme,
	String className,
	Class clazz,
	long lastloaded
) implements ClassMapEntryBuilder.With {}

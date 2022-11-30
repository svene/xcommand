package org.xcommand.template.jst;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class ClassMapEntry {
	public FileMapEntry fme;
	public String className;
	public Class clazz;
	public long lastloaded;
}

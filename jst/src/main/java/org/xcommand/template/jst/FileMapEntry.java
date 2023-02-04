package org.xcommand.template.jst;

import lombok.Builder;
import lombok.Value;

import java.nio.file.Path;

@Builder(toBuilder = true)
@Value
public class FileMapEntry {
	public String key;
	public Path path;
	public long lastmodified;
	public String content;
	public Path rootPath;
}

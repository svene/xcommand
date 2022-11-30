package org.xcommand.template.jst;

import java.io.File;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class FileMapEntry {
	public String key;
	public File file;
	public long lastmodified;
	public String content;
	public String rootDir;
}

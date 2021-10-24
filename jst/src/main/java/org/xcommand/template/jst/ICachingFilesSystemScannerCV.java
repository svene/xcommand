package org.xcommand.template.jst;

import java.util.Map;

public interface ICachingFilesSystemScannerCV
{
	Map<String, FileMapEntry> getChangedFiles();
	Map<String, FileMapEntry> getCurrentFiles();
	void setChangedFiles(Map<String, FileMapEntry> aChangedFiles);
	void setCurrentFiles(Map<String, FileMapEntry> aCurrentFiles);
}

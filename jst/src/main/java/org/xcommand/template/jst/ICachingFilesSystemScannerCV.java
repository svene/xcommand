package org.xcommand.template.jst;

import java.nio.file.Path;
import java.util.Map;

public interface ICachingFilesSystemScannerCV {
    Map<Path, FileMapEntry> getChangedFiles();

    Map<Path, FileMapEntry> getCurrentFiles();

    void setChangedFiles(Map<String, FileMapEntry> aChangedFiles);

    void setCurrentFiles(Map<String, FileMapEntry> aCurrentFiles);
}

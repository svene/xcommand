package org.xcommand.template.jst;

import java.nio.file.Path;

@FunctionalInterface
public interface FileSystemScannerExt {
    void handlePath(Path path);
}

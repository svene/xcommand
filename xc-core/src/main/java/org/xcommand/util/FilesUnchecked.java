package org.xcommand.util;

import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import org.jooq.lambda.Sneaky;

public interface FilesUnchecked {
    static long getLastModifiedTime(Path path, LinkOption... options) {
        return Sneaky.supplier(() -> Files.getLastModifiedTime(path, options).toMillis())
                .get();
    }

    static InputStream newInputStream(Path path, OpenOption... options) {
        return Sneaky.supplier(() -> Files.newInputStream(path, options)).get();
    }

    static Path createFile(Path path, FileAttribute<?>... attrs) {
        return Sneaky.supplier(() -> Files.createFile(path, attrs)).get();
    }

    static Path walkFileTree(Path start, FileVisitor<? super Path> visitor) {
        return Sneaky.supplier(() -> Files.walkFileTree(start, visitor)).get();
    }
}

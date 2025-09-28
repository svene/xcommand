package org.xcommand.template.jst;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.util.FilesUnchecked;

public final class FileSystemScanner implements ICommand, FileSystemScannerExt {

    private FileSystemScanner(List<Path> rootPaths, FileSystemScannerExt ext) {
        this.rootPaths = rootPaths;
        this.ext = ext;
    }

    private FileSystemScanner(List<Path> rootPaths) {
        this.rootPaths = rootPaths;
        this.ext = this;
    }

    private final FileSystemScannerExt ext;
    private List<Path> rootPaths;
    private final INotifier fileFoundNotifier = new BasicNotifier();
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);

    public static FileSystemScanner newInstance(List<Path> rootPaths) {
        return new FileSystemScanner(rootPaths);
    }

    public static FileSystemScanner newInstance(List<Path> rootPaths, FileSystemScannerExt relations) {
        return new FileSystemScanner(rootPaths, relations);
    }

    @Override
    public void execute() {
        rootPaths.forEach(rootPath -> {
            fileSystemScannerCV.setRootPath(rootPath);
            FilesUnchecked.walkFileTree(rootPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (!Files.isDirectory(path)) {
                        ext.handlePath(path);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        });
    }

    public List<Path> getRootPaths() {
        return rootPaths;
    }

    public INotifier getFileFoundNotifier() {
        return fileFoundNotifier;
    }

    public void setRootPaths(List<Path> aRootDirs) {
        rootPaths = aRootDirs;
    }

    @Override
    public void handlePath(Path path) {
        fileSystemScannerCV.setPath(path);
        fileFoundNotifier.execute();
    }
}

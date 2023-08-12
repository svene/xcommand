package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.util.FilesUnchecked;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public final class FileSystemScanner implements ICommand, FileSystemScannerExt {

	private FileSystemScanner(FileSystemScannerExt ext) {
		this.ext = ext == null ? this : ext;
	}

	private final FileSystemScannerExt ext;
	private List<Path> rootPaths;
	private final INotifier fileFoundNotifier = new BasicNotifier();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);


	public static FileSystemScanner newInstance() {
		return new FileSystemScanner(null);
	}
	public static FileSystemScanner newInstance(FileSystemScannerExt relations) {
		return new FileSystemScanner(relations);
	}

	@Override
	public void execute() {
		rootPaths.forEach(rootPath -> {
			fileSystemScannerCV.setRootPath(rootPath);
			boolean ex = Files.exists(rootPath);
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

	public void setRootPaths(Path... aRootPaths) {
		setRootPaths(List.of(aRootPaths));
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

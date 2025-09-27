package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.util.FilesUnchecked;

import java.util.HashMap;
import java.util.List;

public class CachingFilesSystemScanner implements ICommand {
	@Override
	public void execute() {
		var fssc = FileSystemScanner.newInstance(List.of(fileSystemScannerCV.getRootPath()));
		fssc.getFileFoundNotifier().registerObserver(new FileFoundHandler());
		var changedFiles = new HashMap<String, FileMapEntry>();
		cachingFilesSystemScannerCV.setChangedFiles(changedFiles);
		if (cachingFilesSystemScannerCV.getCurrentFiles() == null) {
			cachingFilesSystemScannerCV.setCurrentFiles(new HashMap<>());
		}
		fssc.execute();
		if (changedFiles.size() > 0) {
			changedFilesNotifier.execute();
		}
	}

	public INotifier getChangedFilesNotifier() {
		return changedFilesNotifier;
	}

	private class FileFoundHandler implements ICommand {

		@Override
		public void execute() {
			var path = fileSystemScannerCV.getPath();
			var key = path.toAbsolutePath();
			var currentFiles = cachingFilesSystemScannerCV.getCurrentFiles();
			var changedFiles = cachingFilesSystemScannerCV.getChangedFiles();
			if (currentFiles.containsKey(key)) {
				var fme = currentFiles.get(key);
				if (FilesUnchecked.getLastModifiedTime(fme.path()) > fme.lastmodified()) {//reload file
					changedFiles.put(key, fme);
				}
			} else {
				System.out.println("new file found: " + key);
				var fme = FileMapEntryBuilder.builder()
					.path(path)
					.key(key.toString())
					.lastmodified(FilesUnchecked.getLastModifiedTime(path))
					.rootPath(fileSystemScannerCV.getRootPath())
					.build();
				currentFiles.put(key, fme);
				changedFiles.put(key, fme);
			}
		}
	}

	private final INotifier changedFilesNotifier = new BasicNotifier();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
	ICachingFilesSystemScannerCV cachingFilesSystemScannerCV = dbp.newBeanForInterface(
		ICachingFilesSystemScannerCV.class);

}

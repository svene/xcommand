package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.io.File;
import java.util.HashMap;

public class CachingFilesSystemScanner implements ICommand
{
	@Override
	public void execute()
	{
		var rootDirs = fileSystemScannerCV.getRootDirs();

		FileSystemScanner fssc = new FileSystemScanner();
		fssc.setRootDirs(rootDirs);
		fssc.getFileFoundNotifier().registerObserver(new FileFoundHandler());
		var changedFiles = new HashMap<String, FileMapEntry>();
		cachingFilesSystemScannerCV.setChangedFiles(changedFiles);
		if (cachingFilesSystemScannerCV.getCurrentFiles() == null)
		{
			cachingFilesSystemScannerCV.setCurrentFiles(new HashMap<>());
		}
		fssc.execute();
		if (changedFiles.size() > 0)
		{
			changedFilesNotifier.execute();
		}
	}

	public INotifier getChangedFilesNotifier()
	{
		return changedFilesNotifier;
	}

// --- Implementation ---

	private class FileFoundHandler implements ICommand
	{

		@Override
		public void execute()
		{
			File file = fileSystemScannerCV.getFile();
			String key = file.getAbsolutePath();
			var currentFiles = cachingFilesSystemScannerCV.getCurrentFiles();
			var changedFiles = cachingFilesSystemScannerCV.getChangedFiles();
			if (currentFiles.containsKey(key)) {
				FileMapEntry fme = currentFiles.get(key);
				if (fme.file.lastModified() > fme.lastmodified) {//reload file
					changedFiles.put(key, fme);
				}
			} else {
				System.out.println("new file found: " + key);
				FileMapEntry fme = new FileMapEntry();
				fme.file = file;
				fme.key = key;
				fme.lastmodified = file.lastModified();
				fme.rootDir = fileSystemScannerCV.getRootDir();
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

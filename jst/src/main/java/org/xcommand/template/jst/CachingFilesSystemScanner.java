package org.xcommand.template.jst;

import org.xcommand.core.*;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachingFilesSystemScanner implements ICommand
{
	public void execute()
	{
		List rootDirs = fileSystemScannerCV.getRootDirs();

		FileSystemScanner fssc = new FileSystemScanner();
		fssc.setRootDirs(rootDirs);
		fssc.getFileFoundNotifier().registerObserver(new FileFoundHandler());
		Map changedFiles = new HashMap();
		cachingFilesSystemScannerCV.setChangedFiles(changedFiles);
		if (cachingFilesSystemScannerCV.getCurrentFiles() == null)
		{
			cachingFilesSystemScannerCV.setCurrentFiles(new HashMap());
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

		public void execute()
		{
			File file = fileSystemScannerCV.getFile();
			String key = file.getAbsolutePath();
			Map currentFiles = cachingFilesSystemScannerCV.getCurrentFiles();
			Map changedFiles = cachingFilesSystemScannerCV.getChangedFiles();
			if (!currentFiles.containsKey(key))
			{
				System.out.println("new file found: " + key);
				FileMapEntry fme = new FileMapEntry();
				fme.file = file;
				fme.key = key;
				fme.lastmodified = file.lastModified();
				fme.rootDir = fileSystemScannerCV.getRootDir();
				currentFiles.put(key, fme);
				changedFiles.put(key, fme);
			}
			else
			{
				FileMapEntry fme = (FileMapEntry) currentFiles.get(key);
				if (fme.file.lastModified() > fme.lastmodified)
				{//reload file
					changedFiles.put(key, fme);
				}
			}
		}
	}

	private final INotifier changedFilesNotifier = new BasicNotifier();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
	ICachingFilesSystemScannerCV cachingFilesSystemScannerCV = dbp.newBeanForInterface(
		ICachingFilesSystemScannerCV.class);

}

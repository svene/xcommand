package org.xcommand.template.jst;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.List;

public class FileSystemScanner implements ICommand
{


	public void execute()
	{
		Iterator it1 = rootDirs.iterator();
		// Loop over all configured source directories,
		while (it1.hasNext())
		{
			String rootDir = (String) it1.next();
			fileSystemScannerCV.setRootDir(rootDir);
			FilenameFilter fnf = fileSystemScannerCV.getFilenameFilter();
			Iterator it = new DirectoryIteratorProvider(fnf).newIterator(rootDir);
			while (it.hasNext())
			{
				File file = (File) it.next();
				fileSystemScannerCV.setFile(file);
				fileFoundNotifier.execute();
			}

		}
	}

// --- Access ---

	public List getRootDirs()
	{
		return rootDirs;
	}

	public INotifier getFileFoundNotifier()
	{
		return fileFoundNotifier;
	}

// --- Setting ---

	public void setRootDirs(List aRootDirs)
	{
		rootDirs = aRootDirs;
	}

// --- Implementation ---

	private List rootDirs;
	private INotifier fileFoundNotifier = new BasicNotifier();
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IFileSystemScannerCV fileSystemScannerCV = (IFileSystemScannerCV) dbp.newBeanForInterface(
		IFileSystemScannerCV.class);

}

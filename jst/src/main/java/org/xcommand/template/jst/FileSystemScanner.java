package org.xcommand.template.jst;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileSystemScanner implements ICommand
{
	@Override
	public void execute()
	{
		// Loop over all configured source directories,
		for (String rootDir : rootDirs) {
			fileSystemScannerCV.setRootDir(rootDir);
			FilenameFilter fnf = fileSystemScannerCV.getFilenameFilter();
			Iterator<File> it = new DirectoryIteratorProvider(fnf).newIterator(rootDir);
			while (it.hasNext()) {
				File file = it.next();
				fileSystemScannerCV.setFile(file);
				fileFoundNotifier.execute();
			}

		}
	}

// --- Access ---

	public List<String> getRootDirs()
	{
		return rootDirs;
	}

	public INotifier getFileFoundNotifier()
	{
		return fileFoundNotifier;
	}

// --- Setting ---

	public void setRootDirs(String... aRootDirs) {
		setRootDirs(Arrays.asList(aRootDirs));
	}

	public void setRootDirs(List<String> aRootDirs)
	{
		rootDirs = aRootDirs;
	}

// --- Implementation ---

	private List<String> rootDirs;
	private INotifier fileFoundNotifier = new BasicNotifier();
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);

}

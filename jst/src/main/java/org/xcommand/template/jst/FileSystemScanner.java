package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.util.List;

public class FileSystemScanner implements ICommand {
	@Override
	public void execute() {
		// Loop over all configured source directories,
		rootDirs.forEach(rootDir -> {
			fileSystemScannerCV.setRootDir(rootDir);
			var fnf = fileSystemScannerCV.getFilenameFilter();
			var it = new DirectoryIteratorProvider(fnf).newIterator(rootDir);
			while (it.hasNext()) {
				var file = it.next();
				fileSystemScannerCV.setFile(file);
				fileFoundNotifier.execute();
			}
		});
	}

	public List<String> getRootDirs() {
		return rootDirs;
	}

	public INotifier getFileFoundNotifier() {
		return fileFoundNotifier;
	}

	public void setRootDirs(String... aRootDirs) {
		setRootDirs(List.of(aRootDirs));
	}

	public void setRootDirs(List<String> aRootDirs) {
		rootDirs = aRootDirs;
	}

	private List<String> rootDirs;
	private final INotifier fileFoundNotifier = new BasicNotifier();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);

}

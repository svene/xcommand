package org.xcommand.template.jst;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.Date;

public class JSTUpdateChecker extends TimerTask
{
	{
		System.out.println("JSTUpdateChecker.instance initializer()");
	}
	public void run()
	{
		System.out.println("JSTUpdateChecker.run(): " + new Date());
		var classMap = jstScannerCV.getClassMap();
		if (classMap == null) {
			throw new IllegalStateException("classMap == null");
		}

		FileSystemBasedJSTScanner jstScanner = new FileSystemBasedJSTScanner();
		fileSystemScannerCV.setRootDirs(srcDirs);
		jstScanner.getChangeNotifier().registerObserver(new ChangedHandler());
		jstScanner.execute();
	}

// --- Setting ---

	public void setSrcDirs(List<String> aSrcDirs)
	{
		srcDirs = aSrcDirs;
	}

	public void setJaninoObjectCreator(JSTJaninoObjectCreator aJaninoObjectCreator)
	{
		janinoObjectCreator = aJaninoObjectCreator;
	}

// --- Implementation ---

	private class ChangedHandler implements ICommand
	{
		@Override
		public void execute()
		{
			janinoObjectCreator.initialize();
		}
	}

	private List<String> srcDirs;
	private JSTJaninoObjectCreator janinoObjectCreator;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
	private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
}

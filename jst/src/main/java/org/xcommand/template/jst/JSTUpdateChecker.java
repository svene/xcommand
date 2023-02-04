package org.xcommand.template.jst;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

import java.nio.file.Path;
import java.util.List;
import java.util.TimerTask;
import java.util.Date;

public class JSTUpdateChecker extends TimerTask {
	{
		System.out.println("JSTUpdateChecker.instance initializer()");
	}

	@Override
	public void run() {
		System.out.println("JSTUpdateChecker.run(): " + new Date());
		var classMap = jstScannerCV.getClassMap();
		if (classMap == null) {
			throw new IllegalStateException("classMap == null");
		}

		var jstScanner = new FileSystemBasedJSTScanner();
		fileSystemScannerCV.setRootPaths(srcPaths);
		jstScanner.getChangeNotifier().registerObserver(new ChangedHandler());
		jstScanner.execute();
	}

	public void setSrcPaths(List<Path> aSrcPaths) {
		srcPaths = aSrcPaths;
	}

	public void setJaninoObjectCreator(JSTJaninoObjectCreator aJaninoObjectCreator) {
		janinoObjectCreator = aJaninoObjectCreator;
	}

	private class ChangedHandler implements ICommand {
		@Override
		public void execute() {
			janinoObjectCreator.initialize();
		}
	}

	private List<Path> srcPaths;
	private JSTJaninoObjectCreator janinoObjectCreator;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
	private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
}

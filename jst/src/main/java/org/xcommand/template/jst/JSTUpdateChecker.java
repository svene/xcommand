package org.xcommand.template.jst;

import java.nio.file.Path;
import java.util.List;
import java.util.TimerTask;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

public class JSTUpdateChecker extends TimerTask {
    {
        System.out.println("JSTUpdateChecker.instance initializer()");
    }

    public JSTUpdateChecker(List<Path> srcPaths, JSTJaninoObjectCreator janinoObjectCreator) {
        this.srcPaths = srcPaths;
        this.janinoObjectCreator = janinoObjectCreator;
    }

    @Override
    public void run() {
        System.out.println("JSTUpdateChecker.run(): " + System.currentTimeMillis());
        var classMap = jstScannerCV.getClassMap();
        if (classMap == null) {
            throw new IllegalStateException("classMap == null");
        }

        var jstScanner = new FileSystemBasedJSTScanner();
        fileSystemScannerCV.setRootPaths(srcPaths);
        jstScanner.getChangeNotifier().registerObserver(new ChangedHandler());
        jstScanner.execute();
    }

    private class ChangedHandler implements ICommand {
        @Override
        public void execute() {
            janinoObjectCreator.initialize();
        }
    }

    private final List<Path> srcPaths;
    private final JSTJaninoObjectCreator janinoObjectCreator;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
    private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
}

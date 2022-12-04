package org.xcommand.template.jst.parser;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.jst.FileNameFilters;
import org.xcommand.template.jst.FileSystemScanner;
import org.xcommand.template.jst.IFileSystemScannerCV;

public class FileSystemScannerTester {
	@Test
	public void test1() {
		var scanner = new FileSystemScanner();
		scanner.setRootDirs("src/main/java", "src/test/java");

		fileSystemScannerCV.setFilenameFilter(FileNameFilters.newExtensionFilenameFilter(".java"));
		TC.IStringMockHook smh = Mockito.mock(TC.IStringMockHook.class);
		scanner.getFileFoundNotifier().registerObserver(new FileFoundHandler(smh));
		scanner.execute();

		// Test for the following three files (although more will be found):
		Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/IJSTParserProvider.java");
		Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/FileSystemBasedJSTProvider.java");
		Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/JSTCompiler.java");
	}

	private class FileFoundHandler implements ICommand {
		private final TC.IStringMockHook stringMockHook;

		private FileFoundHandler(TC.IStringMockHook aStringMockHook) {
			stringMockHook = aStringMockHook;
		}

		@Override
		public void execute() {
			var s = fileSystemScannerCV.getFile().getPath().replaceAll("\\\\", "/");
			stringMockHook.hookRoutineForMockVerification(s);
		}
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
}

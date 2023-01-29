package org.xcommand.template.jst.parser;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.jst.FileNameFilters;
import org.xcommand.template.jst.FileSystemScanner;
import org.xcommand.template.jst.IFileSystemScannerCV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemScannerTester {
	private String nameOfFile(java.io.File f) {
		return f.getPath().replaceAll("\\\\", "/");
	}

	@Test
	public void interactionTest() {
		List<String> actual = new ArrayList<>();
		var scanner = FileSystemScanner.newInstance(file -> actual.add(nameOfFile(file)));
		scanner.setRootDirs("src/main/java", "src/test/java");
		fileSystemScannerCV.setFilenameFilter(FileNameFilters.newExtensionFilenameFilter(".java"));
		scanner.execute();
		assertThat(actual.size()).isGreaterThan(3);
		// Test for the following three files (although more will be found):
		assertThat(actual).containsAll(Arrays.asList(
			"src/main/java/org/xcommand/template/jst/IJSTParserProvider.java",
			"src/main/java/org/xcommand/template/jst/FileSystemBasedJSTProvider.java",
			"src/main/java/org/xcommand/template/jst/JSTCompiler.java"
		));
	}

	@Test
	public void callThroughTest() {
		var scanner = FileSystemScanner.newInstance();
		scanner.setRootDirs("src/main/java", "src/test/java");

		fileSystemScannerCV.setFilenameFilter(FileNameFilters.newExtensionFilenameFilter(".java"));
		TC.IStringMockHook smh = Mockito.mock(TC.IStringMockHook.class);
		scanner.getFileFoundNotifier().registerObserver(
			() -> smh.hookRoutineForMockVerification(nameOfFile(fileSystemScannerCV.getFile()))
		);
		scanner.execute();

		// Test for the following three files (although more will be found):
		Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/IJSTParserProvider.java");
		Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/FileSystemBasedJSTProvider.java");
		Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/JSTCompiler.java");
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
}

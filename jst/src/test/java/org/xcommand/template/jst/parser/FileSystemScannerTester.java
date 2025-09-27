package org.xcommand.template.jst.parser;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.jst.FileSystemScanner;
import org.xcommand.template.jst.IFileSystemScannerCV;
import org.xcommand.util.FilesUnchecked;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemScannerTester {

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class FakeFileSystemTests {
		FileSystem fs = Jimfs.newFileSystem(Configuration.unix().toBuilder().setWorkingDirectory("/project").build());
		Path srcPath = fs.getPath("src");
		Path mainJavaPath;
		Path testJavaPath;
		private final String fileName1 = "org/xcommand2/template/jst/IJSTParserProvider.java";
		private final String fileName2 = "org/xcommand2/template/jst/FileSystemBasedJSTProvider.java";
		private final String fileName3 = "org/xcommand2/template/jst/JSTCompiler.java";
		private final String testFileName1 = "org/xcommand2/template/jst/JSTCompilerTest.java";
		private final List<String> mainFiles = Arrays.asList(fileName1, fileName2, fileName3);
		private final List<String> testFiles = Arrays.asList(testFileName1);

		@BeforeAll
		void beforeAll() throws IOException {
			Files.createDirectories(fs.getPath("src/main/java/org/xcommand2/template/jst"));
			Files.createDirectories(fs.getPath("src/test/java/org/xcommand2/template/jst"));

			mainJavaPath = srcPath.resolve("main/java");
			assertThat(Files.exists(mainJavaPath)).isTrue();

			testJavaPath = srcPath.resolve("test/java");
			assertThat(Files.exists(testJavaPath)).isTrue();

			mainFiles.forEach(it -> FilesUnchecked.createFile(mainJavaPath.resolve(it)));
			testFiles.forEach(it -> FilesUnchecked.createFile(testJavaPath.resolve(it)));
		}

		@Test
		public void fileSystemSetup() {
			assertThat(Files.exists(mainJavaPath.resolve(fileName1))).isTrue();
			assertThat(Files.exists(mainJavaPath.resolve(fileName2))).isTrue();
			assertThat(Files.exists(mainJavaPath.resolve(fileName3))).isTrue();
			assertThat(Files.exists(mainJavaPath.resolve(testFileName1))).isFalse();
			assertThat(Files.exists(testJavaPath.resolve(testFileName1))).isTrue();
		}

		@Test
		public void jdkBehavior() {
			assertThat(
				mainJavaPath.resolve(fileName1).toAbsolutePath().toString()
			).isEqualTo("/project/src/main/java/org/xcommand2/template/jst/IJSTParserProvider.java");
			assertThat(
				mainJavaPath.resolve(fileName1).toString()
			).isEqualTo("src/main/java/org/xcommand2/template/jst/IJSTParserProvider.java");

		}
		@Test
		public void customHandler() {
			List<String> actual = new ArrayList<>();
			var scanner = FileSystemScanner.newInstance(List.of(mainJavaPath , testJavaPath), path -> actual.add(path.toString()));
			// todo: should a filenamefilter e.g. "*.java" be supported ?
			scanner.execute();
			// Test for the following three files (although more will be found):
			assertThat(actual).containsAll(Arrays.asList(
				"src/main/java/org/xcommand2/template/jst/IJSTParserProvider.java",
				"src/main/java/org/xcommand2/template/jst/FileSystemBasedJSTProvider.java",
				"src/main/java/org/xcommand2/template/jst/JSTCompiler.java",
				"src/test/java/org/xcommand2/template/jst/JSTCompilerTest.java"
			));
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class RealFileSystemTests {
		Path srcPath = Path.of("src");
		Path mainJavaPath;
		Path testJavaPath;

		@BeforeAll
		void beforeAll() {
			mainJavaPath = srcPath.resolve("main/java");
			assertThat(Files.exists(mainJavaPath)).isTrue();

			testJavaPath = srcPath.resolve("test/java");
			assertThat(Files.exists(testJavaPath)).isTrue();

			assertThat(
				mainJavaPath.toString()
			).isEqualTo("src/main/java");
		}

		@Test
		void customHandler() {
			List<String> actual = new ArrayList<>();
			var scanner = FileSystemScanner.newInstance(List.of(mainJavaPath , testJavaPath), path -> actual.add(path.toString()));
			// todo: should a filenamefilter e.g. "*.java" be supported ?
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
		void defaultHandler() {
			var scanner = FileSystemScanner.newInstance(List.of(mainJavaPath , testJavaPath));

			// todo: should a filenamefilter e.g. "*.java" be supported ?
			TC.IStringMockHook smh = Mockito.mock(TC.IStringMockHook.class);
			scanner.getFileFoundNotifier().registerObserver(
				() -> smh.hookRoutineForMockVerification(fileSystemScannerCV.getPath().toString())
			);
			scanner.execute();

			// Test for the following three files (although more will be found):
			Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/IJSTParserProvider.java");
			Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/FileSystemBasedJSTProvider.java");
			Mockito.verify(smh).hookRoutineForMockVerification("src/main/java/org/xcommand/template/jst/JSTCompiler.java");
		}


	}



	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
}

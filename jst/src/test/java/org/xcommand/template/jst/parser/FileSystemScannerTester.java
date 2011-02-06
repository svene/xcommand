package org.xcommand.template.jst.parser;

import junit.framework.TestCase;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.template.jst.FileSystemScanner;
import org.xcommand.template.jst.IFileSystemScannerCV;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSystemScannerTester extends TestCase
{

	protected void setUp() throws Exception
	{
	}

	public void test1() throws Exception
	{
		lh.setList(new ArrayList());
		FileSystemScanner fssc = new FileSystemScanner();
		List<String> lst = Arrays.asList("src/main/java", "src/test/java");
		fssc.setRootDirs(lst);
		FilenameFilter javaFilenameFilter = new FilenameFilter()
		{
			public boolean accept(File aFile, String aString)
			{
				return aString.endsWith(".java");
			}
		};

		fileSystemScannerCV.setFilenameFilter(javaFilenameFilter);
		fssc.getFileFoundNotifier().registerObserver(new FileFoundReporter());
		fssc.execute();
		assertTrue(lh.getList().contains("src/main/java/org/xcommand/template/jst/IJSTParserProvider.java"));
		assertTrue(lh.getList().contains("src/main/java/org/xcommand/template/jst/FileSystemBasedJSTProvider.java"));
		assertTrue(lh.getList().contains("src/main/java/org/xcommand/template/jst/JSTCompiler.java"));
	}

	private class FileFoundReporter implements ICommand
	{
		public void execute()
		{
			String s = fileSystemScannerCV.getFile().getPath().replaceAll("\\\\", "/");
			System.out.println("file found: " + s);
			lh.getList().add(s);
		}
	}

	private interface ListHolder
	{
		public List getList();
		public void setList(List aList);
	}

// --- Implementation ---

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ListHolder lh = (ListHolder) dbp.newBeanForInterface(ListHolder.class);
	IFileSystemScannerCV fileSystemScannerCV = (IFileSystemScannerCV) dbp.newBeanForInterface(IFileSystemScannerCV.class);
}

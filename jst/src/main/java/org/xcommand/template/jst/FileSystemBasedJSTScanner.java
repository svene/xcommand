package org.xcommand.template.jst;

import org.apache.commons.io.FileUtils;
import org.xcommand.core.*;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.template.jst.parser.JSTParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileSystemBasedJSTScanner implements ICommand
{

	/**
	 * require:
	 *   FileSystemScannerCV.getRootDirs(aCtx);
	 */
	public void execute()
	{
		CachingFilesSystemScanner cfssc = new CachingFilesSystemScanner();
		fileSystemScannerCV.setFilenameFilter(FileNameFilters.javaFilenameFilter);
		cfssc.getChangedFilesNotifier().registerObserver(new FileFoundHandler());

		cfssc.execute();
	}

	public void setGenSourceDir(String aGenSourceDir)
	{
		genSourceDir = aGenSourceDir;
	}
	public INotifier getChangeNotifier()
	{
		return changeNotifier;
	}

// --- Implementation ---

	private class FileFoundHandler implements ICommand
	{

		public void execute()
		{
			var changedFiles = cachingFilesSystemScannerCV.getChangedFiles();

			for (Map.Entry<String, FileMapEntry> me : changedFiles.entrySet()) {
				String absolutePath = me.getKey();
				FileMapEntry fme = me.getValue();

				System.out.println("recompiling file: " + absolutePath);
				ClassMapEntry cme = new ClassMapEntry();
				cme.fme = fme;
				String className = getClassnameFromFilename(fme.rootDir, absolutePath);
				cme.className = className;
				var classMap = jstScannerCV.getClassMap();
				classMap.put(className, cme);

				File file = cme.fme.file;

				// Invoke TemplateToSourceGenerator:
				try {
					TCP.pushContext(new HashMap<>());
					FileInputStream is = new FileInputStream(file);
					jstParserCV.setInputStream(is);
					JSTParser parser = new DefaultJSTParserProvider().newJSTParser();
					TCP.popContext();

					jstParserCV.setGeneratedJavaCode(new StringBuffer());
					parser.Start();
					cme.fme.content = jstParserCV.getGeneratedJavaCode().toString();

					// Write source code as file to disk:
					if (genSourceDir != null) {
						String dirName = genSourceDir + "/";
						File dir = new File(genSourceDir);
						System.out.println("gensrcdir.path=" + dir.getAbsolutePath());
						File rf = new File(genSourceDir + "/" + className + ".java");
						rf.createNewFile();
						FileUtils.writeStringToFile(rf, cme.fme.content, StandardCharsets.UTF_8);
					}

				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
			changeNotifier.execute();
		}
		private String getClassnameFromFilename(String aSrcDir, String aAbsolutePath)
		{
			int idx = aAbsolutePath.indexOf(aSrcDir);
			if (idx == -1)
			{
				throw new RuntimeException("cannot find source path '" + aSrcDir + "' in path of file '" + aAbsolutePath + "'");
			}

			String className = aAbsolutePath.substring(idx + aSrcDir.length() + 1, aAbsolutePath.lastIndexOf("."));
			System.out.println("className = " + className);
			return className;
		}
	}

	private String genSourceDir;
	private final INotifier changeNotifier = new BasicNotifier();

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
	private final ICachingFilesSystemScannerCV cachingFilesSystemScannerCV =
		dbp.newBeanForInterface(ICachingFilesSystemScannerCV.class);
	private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
	private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}

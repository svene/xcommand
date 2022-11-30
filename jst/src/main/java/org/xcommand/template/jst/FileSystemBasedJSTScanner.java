package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.template.jst.parser.JSTParser;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileSystemBasedJSTScanner implements ICommand {

	/**
	 * require:
	 * FileSystemScannerCV.getRootDirs(aCtx);
	 */
	@Override
	public void execute() {
		var cfssc = new CachingFilesSystemScanner();
		fileSystemScannerCV.setFilenameFilter(FileNameFilters.javaFilenameFilter);
		cfssc.getChangedFilesNotifier().registerObserver(new FileFoundHandler());

		cfssc.execute();
	}

	public void setGenSourceDir(String aGenSourceDir) {
		genSourceDir = aGenSourceDir;
	}

	public INotifier getChangeNotifier() {
		return changeNotifier;
	}

	private class FileFoundHandler implements ICommand {

		@Override
		public void execute() {
			var changedFiles = cachingFilesSystemScannerCV.getChangedFiles();

			for (var me : changedFiles.entrySet()) {
				var absolutePath = me.getKey();
				var fme = me.getValue();

				System.out.println("recompiling file: " + absolutePath);
				var cme = new ClassMapEntry();
				cme.fme = fme;
				var className = getClassnameFromFilename(fme.rootDir, absolutePath);
				cme.className = className;
				var classMap = jstScannerCV.getClassMap();
				classMap.put(className, cme);

				var file = cme.fme.file;

				try {
					TCP.pushContext(new HashMap<>());
					var is = new FileInputStream(file);
					jstParserCV.setInputStream(is);
					var parser = new DefaultJSTParserProvider().newJSTParser();
					TCP.popContext();

					jstParserCV.setGeneratedJavaCode(new StringBuffer());
					parser.Start();
					cme.fme.content = jstParserCV.getGeneratedJavaCode().toString();

					// Write source code as file to disk:
					if (genSourceDir != null) {
						var dir = new File(genSourceDir);
						System.out.println("gensrcdir.path=" + dir.getAbsolutePath());
						Files.writeString(Paths.get("%s/%s.java".formatted(genSourceDir, className)), cme.fme.content);
					}

				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
			changeNotifier.execute();
		}

		private String getClassnameFromFilename(String aSrcDir, String aAbsolutePath) {
			var idx = aAbsolutePath.indexOf(aSrcDir);
			if (idx == -1) {
				throw new RuntimeException("cannot find source path '" + aSrcDir + "' in path of file '" + aAbsolutePath + "'");
			}

			var className = aAbsolutePath.substring(idx + aSrcDir.length() + 1, aAbsolutePath.lastIndexOf("."));
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

package org.xcommand.template.jst;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jooq.lambda.Sneaky;
import org.jspecify.annotations.Nullable;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.util.FilesUnchecked;

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
                var className = getClassnameFromFilename(fme.rootPath(), absolutePath);
                var cme = ClassMapEntryBuilder.builder()
                        .fme(fme)
                        .className(className)
                        .build();
                var classMap = jstScannerCV.getClassMap();
                classMap.put(className, cme);

                var parser = TCP.get(() -> {
                    var file = cme.fme().path();
                    var is = FilesUnchecked.newInputStream(file);
                    jstParserCV.setInputStream(is);
                    return new DefaultJSTParserProvider().newJSTParser();
                });

                jstParserCV.setGeneratedJavaCode(new StringBuffer());
                Sneaky.runnable(() -> parser.Start()).run();
                var newFme = cme.fme()
                        .with()
                        .content(jstParserCV.getGeneratedJavaCode().toString())
                        .build();
                var newCme = cme.with().fme(newFme).build();
                classMap.put(className, newCme);

                // Write source code as file to disk:
                if (genSourceDir != null) {
                    var dir = new File(genSourceDir);
                    System.out.println("gensrcdir.path=" + dir.getAbsolutePath());
                    Sneaky.runnable(() -> Files.writeString(
                                    Paths.get("%s/%s.java".formatted(genSourceDir, className)),
                                    cme.fme().content()))
                            .run();
                }
            }
            changeNotifier.execute();
        }

        private String getClassnameFromFilename(Path aSrcDir, Path aAbsolutePath) {
            var idx = aAbsolutePath.toString().indexOf(aSrcDir.toString());
            if (idx == -1) {
                throw new RuntimeException(
                        "cannot find source path '" + aSrcDir + "' in path of file '" + aAbsolutePath + "'");
            }

            var className = aAbsolutePath
                    .toString()
                    .substring(
                            idx + aSrcDir.toString().length() + 1,
                            aAbsolutePath.toString().lastIndexOf("."));
            System.out.println("className = " + className);
            return className;
        }
    }

    private @Nullable String genSourceDir;
    private final INotifier changeNotifier = new BasicNotifier();

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IFileSystemScannerCV fileSystemScannerCV = dbp.newBeanForInterface(IFileSystemScannerCV.class);
    private final ICachingFilesSystemScannerCV cachingFilesSystemScannerCV =
            dbp.newBeanForInterface(ICachingFilesSystemScannerCV.class);
    private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
    private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}

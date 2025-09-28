package org.xcommand.template.jst;

import java.io.File;
import java.util.Iterator;
import org.codehaus.commons.compiler.util.iterator.DirectoryIterator;

@Deprecated
public class JavaTemplateDirectoryIteratorProvider {
    public Iterator<File> newIterator(String aSrcDir) {
        return DirectoryIterator.traverseDirectories(
                new File[] {new File(aSrcDir)},
                FileNameFilters.acceptAllFilenamesFilter,
                FileNameFilters.javaFilenameFilter);
    }
}

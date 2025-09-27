package org.xcommand.template.jst;

import org.codehaus.commons.compiler.util.iterator.DirectoryIterator;

import java.io.File;
import java.util.Iterator;

@Deprecated
public class JavaTemplateDirectoryIteratorProvider {
	public Iterator<File> newIterator(String aSrcDir) {
		return DirectoryIterator.traverseDirectories(
			new File[]{new File(aSrcDir)}, FileNameFilters.acceptAllFilenamesFilter, FileNameFilters.javaFilenameFilter
		);
	}

}

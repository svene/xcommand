package org.xcommand.template.jst;

import org.codehaus.commons.compiler.util.iterator.DirectoryIterator;

import java.util.Iterator;
import java.io.File;
import java.io.FilenameFilter;

public class DirectoryIteratorProvider {

	public DirectoryIteratorProvider(FilenameFilter aFilenameFilter) {
		filenameFilter = aFilenameFilter;
	}

	@SuppressWarnings("unchecked")
	public Iterator<File> newIterator(String aSrcDir) {
		return DirectoryIterator.traverseDirectories(
			new File[]{new File(aSrcDir)}, FileNameFilters.acceptAllFilenamesFilter, filenameFilter
		);
	}

	private final FilenameFilter filenameFilter;
}

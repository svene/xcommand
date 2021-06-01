package org.xcommand.template.jst;

import org.codehaus.commons.compiler.util.iterator.DirectoryIterator;

import java.util.Iterator;
import java.io.File;
import java.io.FilenameFilter;

/**
 * @deprecated 
 */
public class JavaTemplateDirectoryIteratorProvider
{
	public Iterator newIterator(String aSrcDir)
	{
		return DirectoryIterator.traverseDirectories(new File[]{new File(aSrcDir)}, dirnameFilter, javaFilenameFilter);
	}

	private final FilenameFilter dirnameFilter = new FilenameFilter()
	{
		public boolean accept(File aFile, String aString)
		{
			// Accept all directory names:
			return true;
		}
	};

	private final FilenameFilter javaFilenameFilter = new FilenameFilter()
	{
		public boolean accept(File aFile, String aString)
		{
			return aString.endsWith(".java");
		}
	};

}

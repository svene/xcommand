package org.xcommand.template.jst;

import org.codehaus.janino.util.iterator.DirectoryIterator;

import java.util.Iterator;
import java.io.File;
import java.io.FilenameFilter;

public class DirectoryIteratorProvider
{

	public DirectoryIteratorProvider(FilenameFilter aFilenameFilter)
	{
		javaFilenameFilter = aFilenameFilter;
	}

	public DirectoryIteratorProvider(String aEnding)
	{
		ending = aEnding;
	}

	public Iterator newIterator(String aSrcDir)
	{
		return DirectoryIterator.traverseDirectories(new File[]{new File(aSrcDir)}, dirnameFilter, javaFilenameFilter);
	}

	private FilenameFilter dirnameFilter = new FilenameFilter()
	{
		public boolean accept(File aFile, String aString)
		{
			// Accept all directory names:
			return true;
		}
	};

	private FilenameFilter javaFilenameFilter = new FilenameFilter()
	{
		public boolean accept(File aFile, String aString)
		{
			return aString.endsWith(ending);
		}
	};


	private String ending;

}

package org.xcommand.template.jst;


import java.io.File;
import java.io.FilenameFilter;

/**
 * Factory for 'FilenameFilter's
 */
public class FileNameFilters {
	/**
	 * FilenameFilter for all files with an extension of 'aExtension'
	 *
	 * Usage example to return all java files: newExtensionFilenameFilter(".java")
	 *
	 * Implementation note:
	 * it is just checked if the filename ends with 'aExtension'. Therefore
	 * you could pass in '.java' or just 'java' and both would produce the same result.
	 */
	public static FilenameFilter newExtensionFilenameFilter(final String aExtension) {
		return new FilenameFilter()
		{
			@Override
			public boolean accept(File aFile, String aString)
			{
				return aString.endsWith(aExtension);
			}
		};
	}
}

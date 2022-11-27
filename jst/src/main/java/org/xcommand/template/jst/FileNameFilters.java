package org.xcommand.template.jst;

import java.io.FilenameFilter;

/**
 * Factory for 'FilenameFilter's
 */
public class FileNameFilters {
	private FileNameFilters() {
	}

	/**
	 * FilenameFilter for all files with an extension of 'aExtension'
	 * <p>
	 * Usage example to return all java files: newExtensionFilenameFilter(".java")
	 * <p>
	 * Implementation note:
	 * it is just checked if the filename ends with 'aExtension'. Therefore
	 * you could pass in '.java' or just 'java' and both would produce the same result.
	 */
	public static FilenameFilter newExtensionFilenameFilter(final String aExtension) {
		return (aFile, aString) -> aString.endsWith(aExtension);
	}

	public static FilenameFilter javaFilenameFilter = (aFile, aString) -> aString.endsWith(".java");
	public static FilenameFilter acceptAllFilenamesFilter = (aFile, aString) -> true;
}

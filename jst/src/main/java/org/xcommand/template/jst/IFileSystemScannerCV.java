package org.xcommand.template.jst;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public interface IFileSystemScannerCV {
	File getFile();

	FilenameFilter getFilenameFilter();

	String getRootDir();

	List<String> getRootDirs();

	void setFile(File aFile);

	void setFilenameFilter(FilenameFilter aFilenameFilter);

	void setRootDir(String aRootDir);

	void setRootDirs(List<String> aRootDirs);
}

package org.xcommand.template.jst;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public interface IFileSystemScannerCV
{
	public File getFile();
	public FilenameFilter getFilenameFilter();
	public String getRootDir();
	public List getRootDirs();
	public void setFile(File aFile);
	public void setFilenameFilter(FilenameFilter aFilenameFilter);
	public void setRootDir(String aRootDir);
	public void setRootDirs(List aRootDirs);
}

package org.xcommand.template.jst;

import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.List;

public interface IFileSystemScannerCV {
	Path getPath();
	void setPath(Path value);

	Path getRootPath();
	void setRootPath(Path value);

	List<Path> getRootPaths();
	void setRootPaths(List<Path> value);

	FilenameFilter getFilenameFilter();

	void setFilenameFilter(FilenameFilter aFilenameFilter);

}

package org.xcommand.template.jst;

import java.util.Map;

public interface ICachingFilesSystemScannerCV
{
	public Map getChangedFiles();
	public Map getCurrentFiles();
	public void setChangedFiles(Map aChangedFiles);
	public void setCurrentFiles(Map aCurrentFiles);
}

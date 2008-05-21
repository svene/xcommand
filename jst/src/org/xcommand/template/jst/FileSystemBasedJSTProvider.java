package org.xcommand.template.jst;

import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemBasedJSTProvider implements IJSTProvider
{

	public ClassMapEntry getClassMapEntry(Map aCtx, String aClassname)
	{
		ClassMapEntry cme = (ClassMapEntry) classMap.get(aClassname);
		return cme;
	}

	public Map getClassMap()
	{
		return null;
	}

	public void setGenSourceDir(String aGenSourceDir)
	{
		genSourceDir = aGenSourceDir;
	}

	public INotifier getChangeNotifier()
	{
		return changeNotifier;
	}

// --- Implementation ---

	private List srcDirs;
	private Map classMap = new HashMap();
	private INotifier changeNotifier = new BasicNotifier();
	private String genSourceDir;
}




package org.xcommand.template.jst;

import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemBasedJSTProvider implements IJSTProvider {

	@Override
	public ClassMapEntry getClassMapEntry(Map aCtx, String aClassname) {
		ClassMapEntry cme = classMap.get(aClassname);
		return cme;
	}

	@Override
	public Map<String, ClassMapEntry> getClassMap() {
		return null;
	}

	public void setGenSourceDir(String aGenSourceDir) {
		genSourceDir = aGenSourceDir;
	}

	@Override
	public INotifier getChangeNotifier() {
		return changeNotifier;
	}

	private List srcDirs;
	private final Map<String, ClassMapEntry> classMap = new HashMap<>();
	private final INotifier changeNotifier = new BasicNotifier();
	private String genSourceDir;
}




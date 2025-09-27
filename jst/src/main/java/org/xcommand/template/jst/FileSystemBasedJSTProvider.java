package org.xcommand.template.jst;

import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileSystemBasedJSTProvider implements IJSTProvider {

	@Override
	public Optional<ClassMapEntry> getClassMapEntry(Map aCtx, String aClassname) {
		if (!classMap.containsKey(aClassname)) {
			return Optional.empty();
		}
		return Optional.ofNullable(classMap.get(aClassname));
	}

	@Override
	public Map<String, ClassMapEntry> getClassMap() {
		return Map.of();
	}

	public void setGenSourceDir(String aGenSourceDir) {
		genSourceDir = aGenSourceDir;
	}

	@Override
	public INotifier getChangeNotifier() {
		return changeNotifier;
	}

	@SuppressWarnings("unused")
	private List<String> srcDirs = List.of();
	private final Map<String, ClassMapEntry> classMap = new HashMap<>();
	private final INotifier changeNotifier = new BasicNotifier();
	@SuppressWarnings("unused")
	private String genSourceDir = "build";
}




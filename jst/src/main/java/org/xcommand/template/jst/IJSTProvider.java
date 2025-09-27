package org.xcommand.template.jst;

import org.xcommand.pattern.observer.INotifier;

import java.util.Map;
import java.util.Optional;

public interface IJSTProvider {
	Optional<ClassMapEntry> getClassMapEntry(Map<String, Object> aCtx, String aClassname);

	Map<String, ClassMapEntry> getClassMap();

	INotifier getChangeNotifier();
}

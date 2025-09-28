package org.xcommand.template.jst;

import java.util.Map;
import java.util.Optional;
import org.xcommand.pattern.observer.INotifier;

public interface IJSTProvider {
    Optional<ClassMapEntry> getClassMapEntry(Map<String, Object> aCtx, String aClassname);

    Map<String, ClassMapEntry> getClassMap();

    INotifier getChangeNotifier();
}

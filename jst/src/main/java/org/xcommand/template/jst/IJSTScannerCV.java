package org.xcommand.template.jst;

import java.util.Map;

public interface IJSTScannerCV {
    Map<String, ClassMapEntry> getClassMap();

    void setClassMap(Map<String, ClassMapEntry> aClassMap);
}

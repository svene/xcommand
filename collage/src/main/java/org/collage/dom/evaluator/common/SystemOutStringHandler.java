package org.collage.dom.evaluator.common;

import java.util.Map;

public class SystemOutStringHandler implements IStringHandler {

    @Override
    public void handleString(Map<String, Object> aCtx, String aString) {
        System.out.println(aString);
    }
}

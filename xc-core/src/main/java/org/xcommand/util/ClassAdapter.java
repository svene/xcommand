package org.xcommand.util;

public class ClassAdapter extends NestableObjectAdapter {
    public ClassAdapter(IObjectAdapter aNestedAdapter) {
        super(aNestedAdapter);
    }

    @Override
    public Object adaptedObject(Object aSourceObject) {
        return super.adaptedObject(aSourceObject).getClass();
    }
}

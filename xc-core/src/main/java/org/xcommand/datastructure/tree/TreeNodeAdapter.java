package org.xcommand.datastructure.tree;

import org.xcommand.util.IObjectAdapter;
import org.xcommand.util.NestableObjectAdapter;

class TreeNodeAdapter extends NestableObjectAdapter {

    TreeNodeAdapter(IObjectAdapter aNestedAdapter) {
        super(aNestedAdapter);
    }

    @Override
    public Object adaptedObject(Object aSourceObject) {
        return super.adaptedObject(aSourceObject);
    }
}

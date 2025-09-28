package org.collage.dom.evaluator.common;

import java.util.Map;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.misc.IMessageCommandCV;

public class ListAddingStringHandler implements IStringHandler {

    @Override
    public void handleString(Map<String, Object> aCtx, String aString) {
        var lst = messageCommandCV.getList();
        lst.add(aString);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IMessageCommandCV messageCommandCV = dbp.newBeanForInterface(IMessageCommandCV.class);
}

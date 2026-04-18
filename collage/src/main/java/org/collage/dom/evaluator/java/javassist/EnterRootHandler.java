package org.collage.dom.evaluator.java.javassist;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

class EnterRootHandler implements ICommand {
    @Override
    public void execute() {
        methodBodyCV.setMethodBody(new StringBuffer(1024));
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IMethodBodyCV methodBodyCV = dbp.newBeanForInterface(IMethodBodyCV.class);
}

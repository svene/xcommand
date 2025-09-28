package org.xcommand.threadcontext;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

public class TIn2OutCommand implements ICommand {
    @Override
    public void execute() {
        tIn2OutCV.setOutput(tIn2OutCV.getInput());
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final ITIn2OutCV tIn2OutCV = dbp.newBeanForInterface(ITIn2OutCV.class);
}

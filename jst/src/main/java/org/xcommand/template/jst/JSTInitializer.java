package org.xcommand.template.jst;

import java.util.HashMap;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

public class JSTInitializer implements ICommand {
    @Override
    public void execute() {
        jstScannerCV.setClassMap(new HashMap<>());
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
}

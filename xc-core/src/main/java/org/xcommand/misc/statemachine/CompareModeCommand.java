package org.xcommand.misc.statemachine;

import org.xcommand.core.*;
import org.xcommand.core.multi.ModeCV;

public class CompareModeCommand implements ICommand {

    public CompareModeCommand(String aMode) {
        this(ModeCV.KEY_MODE, aMode);
    }

    public CompareModeCommand(String aModeKey, String aMode) {
        modeKey = aModeKey;
        mode = aMode;
    }

    @Override
    public void execute() {
        String ctxMode = ModeCV.getMode(modeKey);
        resultCV.setResult(mode.equals(ctxMode));
    }

    private final String modeKey;
    private final String mode;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);
}

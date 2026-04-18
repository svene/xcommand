package org.xcommand.core.multi;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

public class ModeBasedCommandDispatcher implements ICommand {

    private static final Logger log = LoggerFactory.getLogger(ModeBasedCommandDispatcher.class);

    public ModeBasedCommandDispatcher() {}

    public ModeBasedCommandDispatcher(String aModeKey) {
        modeKey = aModeKey;
    }

    public Map<String, ICommand> getModeCommandMap() {
        return modeCommandMap;
    }

    public void setModeCommandMap(Map<String, ICommand> aModeCommandMap) {
        modeCommandMap = aModeCommandMap;
    }

    @Override
    public void execute() {
        try {
            //			String mode = ModeContextView.getMode(aCtx);
            String mode = (String) TCP.getContext().get(modeKey);
            ICommand command = getModeCommandMap().get(mode);
            if (command != null) {
                command.execute();
            }
        } catch (RuntimeException e) {
            log.error("mode: {}", ModeContextView.getMode(), e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, ICommand> modeCommandMap = new HashMap<>();
    private String modeKey = ModeContextView.KEY_MODE;
}

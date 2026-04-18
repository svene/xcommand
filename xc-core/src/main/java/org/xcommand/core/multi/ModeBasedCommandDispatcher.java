package org.xcommand.core.multi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.xcommand.core.ICommand;

public class ModeBasedCommandDispatcher implements ICommand {

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
        String mode = ModeCV.getMode(modeKey);
        Optional.ofNullable(getModeCommandMap().get(mode)).ifPresent(ICommand::execute);
    }

    private Map<String, ICommand> modeCommandMap = new HashMap<>();
    private String modeKey = ModeCV.KEY_MODE;
}

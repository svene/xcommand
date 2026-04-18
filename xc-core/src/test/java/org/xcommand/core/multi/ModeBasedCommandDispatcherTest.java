package org.xcommand.core.multi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.threadcontext.ITIn2OutCV;

class ModeBasedCommandDispatcherTest {

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final ITIn2OutCV inoutCV = dbp.newBeanForInterface(ITIn2OutCV.class);

    @Test
    void executesCommandMatchingCurrentMode() {
        ICommand cmd1 = () -> inoutCV.setOutput("cmd1");
        ICommand cmd2 = () -> inoutCV.setOutput("cmd2");

        var dispatcher = new ModeBasedCommandDispatcher();
        dispatcher.setModeCommandMap(Map.of("mode1", cmd1, "mode2", cmd2));

        TCP.start(() -> {
            ModeCV.setMode("mode1");
            dispatcher.execute();
            assertThat(inoutCV.getOutput()).isEqualTo("cmd1");

            ModeCV.setMode("mode2");
            dispatcher.execute();
            assertThat(inoutCV.getOutput()).isEqualTo("cmd2");
        });
    }

    @Test
    void doesNothingWhenModeHasNoMatchingCommand() {
        var dispatcher = new ModeBasedCommandDispatcher();
        dispatcher.setModeCommandMap(Map.of("mode1", () -> inoutCV.setOutput("cmd1")));

        TCP.start(() -> {
            ModeCV.setMode("unknown");
            dispatcher.execute();
            assertThat(inoutCV.getOutput()).isNull();
        });
    }

    @Test
    void doesNothingWhenModeIsNotSetInContext() {
        var commandMap = new HashMap<String, ICommand>();
        commandMap.put("mode1", () -> inoutCV.setOutput("cmd1"));

        var dispatcher = new ModeBasedCommandDispatcher();
        dispatcher.setModeCommandMap(commandMap);

        TCP.start(() -> {
            // no mode set
            dispatcher.execute();
            assertThat(inoutCV.getOutput()).isNull();
        });
    }

    @Test
    void usesCustomModeKey() {
        String customKey = "myCustomKey";
        ICommand cmd = () -> inoutCV.setOutput("custom");

        var dispatcher = new ModeBasedCommandDispatcher(customKey);
        dispatcher.setModeCommandMap(Map.of("myMode", cmd));

        TCP.start(() -> {
            TCP.getContext().put(customKey, "myMode");
            dispatcher.execute();
            assertThat(inoutCV.getOutput()).isEqualTo("custom");
        });
    }
}

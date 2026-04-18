package org.xcommand.misc.statemachine;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IResultCV;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.core.multi.ModeContextView;

class CompareModeCommandTest {

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IResultCV resultCV = dbp.newBeanForInterface(IResultCV.class);

    @Test
    void returnsTrueWhenModeMatchesDefaultKey() {
        var cmd = new CompareModeCommand("active");
        TCP.start(() -> {
            ModeContextView.setMode("active");
            cmd.execute();
            assertThat(resultCV.getResult()).isEqualTo(true);
        });
    }

    @Test
    void returnsFalseWhenModeDoesNotMatch() {
        var cmd = new CompareModeCommand("active");
        TCP.start(() -> {
            ModeContextView.setMode("inactive");
            cmd.execute();
            assertThat(resultCV.getResult()).isEqualTo(false);
        });
    }

    @Test
    void usesCustomModeKey() {
        String customKey = "myKey";
        var cmd = new CompareModeCommand(customKey, "running");
        TCP.start(() -> {
            TCP.getContext().put(customKey, "running");
            cmd.execute();
            assertThat(resultCV.getResult()).isEqualTo(true);
        });
    }

    @Test
    void returnsFalseWhenCustomKeyModeDoesNotMatch() {
        String customKey = "myKey";
        var cmd = new CompareModeCommand(customKey, "running");
        TCP.start(() -> {
            TCP.getContext().put(customKey, "stopped");
            cmd.execute();
            assertThat(resultCV.getResult()).isEqualTo(false);
        });
    }
}

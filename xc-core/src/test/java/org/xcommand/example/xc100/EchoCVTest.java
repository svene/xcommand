package org.xcommand.example.xc100;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.xcommand.api.ContextViews;
import org.xcommand.core.*;
import org.xcommand.example.commands.IEchoCV;

class EchoCVTest {
    @Test
    void contextInThread() {
        String message = "Hi! I am a xcommand example. And who are you?";
        TCP.start(() -> {
            ContextViews.get().echoCV.setMessage(message);
            assertThat(ContextViews.get().echoCV.getMessage()).isEqualTo(message);
        });
    }

    @Test
    void explicitContext() {
        String message = "Hi! I am a xcommand example. And who are you?";
        var ctx = new HashMap<String, Object>();

        IDynaBeanProvider dbp = DynaBeanProvider.fromOptions(DynaBeanOptionsBuilder.builder()
                .contextProvider(() -> ctx)
                .dynaBeanKeyProvider(DynaBeanKeyProviders.ClassAndMethodKeyProvider)
                .build());

        IEchoCV echoCV = dbp.newBeanForInterface(IEchoCV.class);

        echoCV.setMessage(message);
        assertThat(echoCV.getMessage()).isEqualTo(message);
        assertThat(ctx).isEqualTo(Map.of("org.xcommand.example.commands.IEchoCV.Message", message));
    }
}

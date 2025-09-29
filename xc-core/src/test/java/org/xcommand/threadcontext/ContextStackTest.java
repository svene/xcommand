package org.xcommand.threadcontext;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.concurrent.*;
import org.jooq.lambda.Sneaky;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;

class ContextStackTest {

    @Test
    void testTIn2OutCommand() {
        String v1 = "v1";
        TCP.start(() -> {
            tIn2OutCV.setInput(v1);
            new TIn2OutCommand().execute();
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v1);
        });
    }

    @Test
    void testContextStack() {
        String v1 = "v1";
        TCP.start(() -> {
            tIn2OutCV.setOutput(v1);
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v1);

            // Save current context:
            var savedCtx = TCP.getContext();

            // Create new context and push it on stack:
            var ctx = new HashMap<String, Object>();
            TCP.pushContext(ctx);
            // Verify that pushed context is current one:
            assertThat(ctx).isSameAs(TCP.getContext());

            // Set a value on the current context:
            String v2 = "v2";
            tIn2OutCV.setInput(v2);
            // Verify that there is not output yet in the new context:
            assertThat(tIn2OutCV.getOutput()).isNull();
            new TIn2OutCommand().execute();
            // Verify that the value got set:
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v2);

            // Remove the new context from the stack:
            TCP.popContext();
            // Verify that saved context is current one:
            assertThat(savedCtx).isSameAs(TCP.getContext());
            // Verify that old value is returned and not 'v2':
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v1);
        });
    }

    @Test
    void testInheritableContextStack() {
        String v1 = "v1";
        TCP.start(() -> {
            tIn2OutCV.setOutput(v1);
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v1);

            // Save current context:
            var savedCtx = TCP.getContext();

            TCP.pushContext(TCP.newInheritableContext());
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v1);

            // Set a value on the current context:
            String v2 = "v2";
            tIn2OutCV.setInput(v2);
            // Verify that output is not null but the output of the inherited context:
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v1);
            new TIn2OutCommand().execute();
            // Verify that execute put the output on the new context:
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v2);

            TCP.popContext();
            assertThat(savedCtx).isSameAs(TCP.getContext());
            // Verify that old value is returned and not 'v2':
            assertThat(tIn2OutCV.getOutput()).isEqualTo(v1);
        });
    }

    @Test
    public void verifyThatEachThreadHasItsOwnContext() throws Exception {
        ICommand cmd = new TIn2OutCommand();
        Runnable r1 = () -> {
            tIn2OutCV.setInput("runnable 1");
            cmd.execute();
            assertThat(tIn2OutCV.getOutput()).isEqualTo("runnable 1");
        };
        TCP.start(Sneaky.runnable(() -> {
            tIn2OutCV.setInput("main thread");
            cmd.execute();

            ExecutorService es = Executors.newSingleThreadExecutor();
            // Note that executing the following line does not overwrite the context of the main thread:
            Future<?> future = es.submit(new Runnable() {
                @Override
                public void run() {
                    TCP.start(Sneaky.runnable(r1::run));
                }
            });
            // Wait until 'es' is done:
            future.get(3, TimeUnit.SECONDS);
            es.shutdown();
            assertThat(tIn2OutCV.getOutput()).isEqualTo("main thread");
        }));
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final ITIn2OutCV tIn2OutCV = dbp.newBeanForInterface(ITIn2OutCV.class);
}

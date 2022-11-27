package org.xcommand.threadcontext;

import org.junit.jupiter.api.Test;
import org.xcommand.core.*;

import java.util.HashMap;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class ContextStackTest {

	@Test
	public void testTIn2OutCommand() {
		String v1 = "v1";
		tIn2OutCV.setInput(v1);
		new TIn2OutCommand().execute();
		assertEquals(v1, tIn2OutCV.getOutput());
	}

	@Test
	public void testContextStack() {
		String v1 = "v1";
		tIn2OutCV.setOutput(v1);
		assertEquals(v1, tIn2OutCV.getOutput());

		// Save current context:
		var savedCtx = TCP.getContext();

		// Create new context and push it on stack:
		var ctx = new HashMap<String, Object>();
		TCP.pushContext(ctx);
		// Verify that pushed context is current one:
		assertSame(ctx, TCP.getContext());

		// Set a value on the current context:
		String v2 = "v2";
		tIn2OutCV.setInput(v2);
		// Verify that there is not output yet in the new context:
		assertNull(tIn2OutCV.getOutput());
		new TIn2OutCommand().execute();
		// Verify that the value got set:
		assertEquals(v2, tIn2OutCV.getOutput());

		// Remove the new context from the stack:
		TCP.popContext();
		// Verify that saved context is current one:
		assertSame(savedCtx, TCP.getContext());
		// Verify that old value is returned and not 'v2':
		assertEquals(v1, tIn2OutCV.getOutput());

	}

	@Test
	public void testInheritableContextStack() {
		String v1 = "v1";
		tIn2OutCV.setOutput(v1);
		assertEquals(v1, tIn2OutCV.getOutput());

		// Save current context:
		var savedCtx = TCP.getContext();

		TCP.pushContext(TCP.newInheritableContext());
		assertEquals(v1, tIn2OutCV.getOutput());

		// Set a value on the current context:
		String v2 = "v2";
		tIn2OutCV.setInput(v2);
		// Verify that output is not null but the output of the inherited context:
		assertEquals(v1, tIn2OutCV.getOutput());
		new TIn2OutCommand().execute();
		// Verify that execute put the output on the new context:
		assertEquals(v2, tIn2OutCV.getOutput());

		TCP.popContext();
		assertSame(savedCtx, TCP.getContext());
		// Verify that old value is returned and not 'v2':
		assertEquals(v1, tIn2OutCV.getOutput());
	}

	@Test
	public void verifyThatEachThreadHasItsOwnContext() throws Exception {
		ICommand cmd = new TIn2OutCommand();
		Runnable r1 = () -> {
			tIn2OutCV.setInput("runnable 1");
			cmd.execute();
			assertEquals("runnable 1", tIn2OutCV.getOutput());
		};
		tIn2OutCV.setInput("main thread");
		cmd.execute();


		ExecutorService es = Executors.newSingleThreadExecutor();
		// Note that executing the following line does not overwrite the context of the main thread:
		Future<?> future = es.submit(r1);
		// Wait until 'es' is done:
		future.get(3, TimeUnit.SECONDS);
		es.shutdown();
		assertEquals("main thread", tIn2OutCV.getOutput());
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final ITIn2OutCV tIn2OutCV = dbp.newBeanForInterface(ITIn2OutCV.class);
}

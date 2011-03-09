package org.xcommand.threadcontext;

import org.junit.Test;
import org.xcommand.core.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class ContextStackTest
{

	static String s = String.format("hello world, from '%s'", ContextStackTest.class.getName());


	@Test
	public void testTIn2OutCommand()
	{
		tIn2OutCV.setInput(s);
		new TIn2OutCommand().execute();
		assertEquals(s, tIn2OutCV.getOutput());
	}

	@Test
	public void testContextStack()
	{
		// Save current context:
		Map savedCtx = TCP.getContext();

		// Create new context and push it on stack:
		Map ctx = new HashMap();
		TCP.pushContext(ctx);
		// Verify that pushed context is current one:
		assertSame(ctx, TCP.getContext());

		// Set a value on the current context:
		String sven = "sven";
		tIn2OutCV.setInput(sven);
		// Verify that there is not output yet in the new context:
		assertNull(tIn2OutCV.getOutput());
		new TIn2OutCommand().execute();
		// Verify that the value got set:
		assertEquals(sven, tIn2OutCV.getOutput());

		// Remove the new context from the stack:
		TCP.popContext();
		// Verify that saved context is current one:
		assertSame(savedCtx, TCP.getContext());
		// Verify that old value is returned and not 'sven':
		assertEquals(s, tIn2OutCV.getOutput());

	}

	@Test
	public void testInheritableContextStack()
	{
		// Save current context:
		Map savedCtx = TCP.getContext();

		TCP.pushNewInheritableContext();
		assertEquals(s, tIn2OutCV.getOutput());

		// Set a value on the current context:
		String sven = "sven";
		tIn2OutCV.setInput(sven);
		// Verify that output is not null but the output of the inherited context:
		assertEquals(s, tIn2OutCV.getOutput());
		new TIn2OutCommand().execute();
		// Verify that execute put the output on the new context:
		assertEquals(sven, tIn2OutCV.getOutput());

		TCP.popContext();
		assertSame(savedCtx, TCP.getContext());
		// Verify that old value is returned and not 'sven':
		assertEquals(s, tIn2OutCV.getOutput());
	}

	@Test public void verifyThatEachThreadHasItsOwnContext() throws InterruptedException {
		final ICommand cmd = new TIn2OutCommand();
		Runnable r1 = new Runnable()
		{
			public void run()
			{
				tIn2OutCV.setInput("runnable 1");
				cmd.execute();
				assertEquals("runnable 1", tIn2OutCV.getOutput());
			}
		};
		tIn2OutCV.setInput("main thread");
		cmd.execute();

		// Note that executing the following line does not overwrite the context of the main thread:
		new Thread(r1).start();
		Thread.sleep(200);// Make sure thread finished. todo: improve this with proper wait/notify
		assertEquals("main thread", tIn2OutCV.getOutput());
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ITIn2OutCV tIn2OutCV = (ITIn2OutCV) dbp.newBeanForInterface(ITIn2OutCV.class);
}

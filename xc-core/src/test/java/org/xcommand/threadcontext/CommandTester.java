package org.xcommand.threadcontext;

import org.junit.Test;
import org.xcommand.core.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CommandTester
{

	static String s = "hello world, from '" + CommandTester.class.getName() + "'";


	@Test
	public void test1()
	{
		tIn2OutCV.setInput(s);
		ICommand cmd = new TIn2OutCommand();
		cmd.execute();
		assertEquals(s, tIn2OutCV.getOutput());

		Map savedCtx = TCP.getContext();

		Map ctx = new HashMap();
		TCP.pushContext(ctx);
		assertSame(ctx, TCP.getContext());

		String sven = "sven";
		tIn2OutCV.setInput(sven);
		cmd.execute();
		assertEquals(sven, tIn2OutCV.getOutput());

		TCP.popContext();
		assertSame(savedCtx, TCP.getContext());
		assertEquals(s, tIn2OutCV.getOutput());

		//System.out.println("===");
		TCP.pushNewInheritableContext();
		assertEquals(s, tIn2OutCV.getOutput());

		tIn2OutCV.setInput(sven);
		assertEquals(s, tIn2OutCV.getOutput());
		cmd.execute();
		assertEquals(sven, tIn2OutCV.getOutput());

		TCP.popContext();
		assertSame(savedCtx, TCP.getContext());
		assertEquals(s, tIn2OutCV.getOutput());
	}

	/** test with two different threads */
	@Test public void test2()
	{
		final ICommand cmd = new TIn2OutCommand();
		Runnable r1 = new Runnable()
		{
			public void run()
			{
				tIn2OutCV.setInput("runnable 1");
				cmd.execute();
				System.out.println("runnable 1: " + tIn2OutCV.getOutput());
				System.out.flush();
			}
		};
		tIn2OutCV.setInput("main thread");
		cmd.execute();

		// Note that in the following two lines `t1' does not overwrite the context of the main thread:
		Thread t1 = new Thread(r1);
		t1.start();
		System.out.println("main thread: " + tIn2OutCV.getOutput());

	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ITIn2OutCV tIn2OutCV = (ITIn2OutCV) dbp.newBeanForInterface(ITIn2OutCV.class);
}

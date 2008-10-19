package org.xcommand.threadcontext;

import junit.framework.TestCase;
import org.xcommand.core.TCP;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

import java.util.HashMap;
import java.util.Map;

public class CommandTester extends TestCase
{

	static String s = "hello world, from '" + CommandTester.class.getName() + "'";


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

		System.out.println("===");
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
	public void test2()
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

	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private ITIn2OutCV tIn2OutCV = (ITIn2OutCV) dbp.newBeanForInterface(ITIn2OutCV.class);
}

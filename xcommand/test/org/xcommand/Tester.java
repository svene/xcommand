package org.xcommand;

import junit.framework.TestSuite;
import org.xcommand.threadcontext.CommandTester;

public class Tester extends TestSuite
{
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(org.xcommand.core.Tester.suite());
		suite.addTest(org.xcommand.datastructure.tree.Tester.suite());
		suite.addTestSuite(CommandTester.class);
		return suite;
	}
}

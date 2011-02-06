package org.xcommand.core;

import junit.framework.TestSuite;

public class Tester extends TestSuite
{
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(DynaBeanProviderTester.class);
		return suite;
	}
}

package org.xcommand.template.jst.parser;

import junit.framework.TestSuite;

public class Tester extends TestSuite
{
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(FileSystemScannerTester.class);
		suite.addTestSuite(JSTParserTester.class);
		return suite;
	}
}

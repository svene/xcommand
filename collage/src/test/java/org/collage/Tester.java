package org.collage;

import junit.framework.TestSuite;

public class Tester extends TestSuite
{
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(DomDumperLowLevelTest.class);
		suite.addTestSuite(TextTemplateTest.class);
		suite.addTestSuite(JavassistTest.class);
		return suite;
	}
}

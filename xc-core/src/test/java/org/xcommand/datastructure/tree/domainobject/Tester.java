package org.xcommand.datastructure.tree.domainobject;

import junit.framework.TestSuite;

public class Tester extends TestSuite
{
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(DomainObjectTreeNodeTest.class);
		return suite;
	}
}

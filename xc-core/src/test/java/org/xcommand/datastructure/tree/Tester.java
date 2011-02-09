package org.xcommand.datastructure.tree;

import junit.framework.TestSuite;
import org.xcommand.datastructure.tree.specifictreenode.SpecificTreeNodeTest;

public class Tester extends TestSuite
{
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(org.xcommand.datastructure.tree.domainobject.Tester.suite());
		suite.addTestSuite(SpecificTreeNodeTest.class);
		return suite;
	}
}

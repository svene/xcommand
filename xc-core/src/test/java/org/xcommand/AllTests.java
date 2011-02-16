package org.xcommand;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xcommand.threadcontext.CommandTester;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.xcommand.core.Tester.class,
	org.xcommand.datastructure.tree.Tester.class,
	CommandTester.class
})
public class AllTests
{
}

package org.xcommand.datastructure.tree.domainobject

import spock.lang.Specification
import org.xcommand.core.ClassAndMethodKeyProvider
import org.xcommand.core.DynaBeanProvider
import org.xcommand.core.IDynaBeanProvider
import org.xcommand.datastructure.tree.ITreeNodeCV
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser
import org.xcommand.core.ICommand


class DomainObjectTreeNodeSpockTest extends Specification {

	// fields:
	TestDataProvider tdp = new TestDataProvider()
	IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser()
	int counter


	def "testEnterExitNodeTraversal"() {
		setup:
			ICommandHook enterHook = Mock(ICommandHook)
			ICommandHook exitHook = Mock(ICommandHook)
			MyCommand enterCmd = new MyCommand(enterHook)
			MyCommand exitCmd = new MyCommand(exitHook)

		when:
			treeNodeCV.setTreeNode(tdp.getRoot1());
			tt.getEnterNodeNotifier().registerObserver(enterCmd)
			tt.getExitNodeNotifier().registerObserver(exitCmd)
			tt.execute();

		then:
			1 * enterHook.testHook(0, tdp.getRoot1().getDomainObject())
		then:
			1 * enterHook.testHook(1, tdp.getRoot1Child().getDomainObject())
		then:
			1 * enterHook.testHook(2, tdp.getRoot1ChildChild().getDomainObject())
		then:
			1 * exitHook.testHook(3, tdp.getRoot1ChildChild().getDomainObject())
		then:
			1 * exitHook.testHook(4, tdp.getRoot1Child().getDomainObject())
		then:
			1 * exitHook.testHook(5, tdp.getRoot1().getDomainObject())
			0 * _._

	}

	public class ICommandHook {
		public void testHook(int position, Object aDomainObject) {}
	}

	class MyCommand implements ICommand {
		ICommandHook commandHook;
		MyCommand(ICommandHook aCommandHook) {
			commandHook = aCommandHook
		}

		@Override
		public void execute() {
			// Pass context state as arguments to 'commandHook.executeWithArguments()' so that they can be verified
			// by the mocking-fw. Just with 'execute()' this is not possible since we cannot access the context from the verification:
			commandHook.testHook(counter, treeNodeCV.getDomainObject());
			counter++;
		}

	}

}
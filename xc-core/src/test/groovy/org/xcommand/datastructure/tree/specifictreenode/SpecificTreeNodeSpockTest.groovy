package org.xcommand.datastructure.tree.specifictreenode

import spock.lang.Specification
import org.xcommand.core.ClassAndMethodKeyProvider
import org.xcommand.core.DynaBeanProvider
import org.xcommand.core.IDynaBeanProvider
import org.xcommand.datastructure.tree.ITreeNodeCV
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser
import org.xcommand.core.ICommand
import org.xcommand.datastructure.tree.specifictreenode.TestDataProvider.TreeNode2
import org.xcommand.datastructure.tree.specifictreenode.TestDataProvider.TreeNode1
import org.xcommand.datastructure.tree.specifictreenode.TestDataProvider.RootTreeNode

import org.xcommand.datastructure.tree.MapBasedHandlerProvider
import org.xcommand.datastructure.tree.TreeNodeCommandFactory

class SpecificTreeNodeSpockTest extends Specification {

	IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser()
	int counter
	TestDataProvider tdp = new TestDataProvider();

	def "verify that that Root1-traversal notifies Enter/Exit-NodeObservers in proper order"() {
		given:
			ICommandHook enterHook = Mock(ICommandHook)
			ICommandHook exitHook = Mock(ICommandHook)
			MyCommand enterCmd = new MyCommand(enterHook)
			MyCommand exitCmd = new MyCommand(exitHook)
			tt.getEnterNodeNotifier().registerObserver(enterCmd)
			tt.getExitNodeNotifier().registerObserver(exitCmd)
			treeNodeCV.setTreeNode(tdp.getRoot1())

		when:
			tt.execute();

		then:
			1 * enterHook.testHook(0, tdp.getRoot1())
		then:
			1 * enterHook.testHook(1, tdp.getRoot1Child())
		then:
			1 * enterHook.testHook(2, tdp.getRoot1ChildChild())
		then:
			1 * exitHook.testHook(3, tdp.getRoot1ChildChild())
		then:
			1 * exitHook.testHook(4, tdp.getRoot1Child())
		then:
			1 * exitHook.testHook(5, tdp.getRoot1())
			0 * _._

	}

	def "verify that that Root2-traversal notifies Enter/Exit-NodeObservers in proper order"() {
		given:
			ICommandHook enterHook = Mock(ICommandHook)
			ICommandHook exitHook = Mock(ICommandHook)
			MyCommand enterCmd = new MyCommand(enterHook)
			MyCommand exitCmd = new MyCommand(exitHook)
			tt.getEnterNodeNotifier().registerObserver(enterCmd)
			tt.getExitNodeNotifier().registerObserver(exitCmd)
			treeNodeCV.setTreeNode(tdp.getRoot2())

		when:
			tt.execute();

		then:
			1 * enterHook.testHook(0, tdp.getRoot2())
		then:
			1 * enterHook.testHook(1, tdp.getRoot2Child1())
		then:
			1 * exitHook.testHook(2, tdp.getRoot2Child1())
		then:
			1 * enterHook.testHook(3, tdp.getRoot2Child2())
		then:
			1 * exitHook.testHook(4, tdp.getRoot2Child2())
		then:
			1 * exitHook.testHook(5, tdp.getRoot2())
			0 * _._

	}

	def "verify that handler is properly notified during traversal"() {
		given:
			ICommand rootTreeNodeHandler = Mock(ICommand)
			ICommand treeNode1Handler = Mock(ICommand)
			ICommand treeNode2Handler = Mock(ICommand)

			Map<Object, ICommand> map = new HashMap<Object, ICommand>();
			map.put(RootTreeNode.class, rootTreeNodeHandler);
			map.put(TreeNode1.class, treeNode1Handler);
			map.put(TreeNode2.class, treeNode2Handler);

			MapBasedHandlerProvider hp = new MapBasedHandlerProvider();
			hp.setHandlerMap(map);
			ICommand cmd = TreeNodeCommandFactory.newTreeNodeKeyedCommand(hp);
			tt.getEnterNodeNotifier().registerObserver(cmd);

		when:
			tt.execute()

		then:
			1 * rootTreeNodeHandler.execute()
		then:
			1 * treeNode1Handler.execute()
		then:
			1 * treeNode2Handler.execute()
			0 * _._
	}


	// Helpers for this test:
	private class ICommandHook {
		public void testHook(int position, Object aDomainObject) {}
	}

	private class MyCommand implements ICommand {
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

package org.xcommand.datastructure.tree.domainobject

import spock.lang.Specification
import org.xcommand.core.ClassAndMethodKeyProvider
import org.xcommand.core.DynaBeanProvider
import org.xcommand.core.IDynaBeanProvider
import org.xcommand.datastructure.tree.ITreeNodeCV
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser
import org.xcommand.core.ICommand
import org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject
import org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject
import org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject
import org.xcommand.datastructure.tree.MapBasedHandlerProvider
import org.xcommand.datastructure.tree.TreeNodeCommandFactory


class DomainObjectTreeNodeSpockTest extends Specification {

	// fields:
	TestDataProvider tdp = new TestDataProvider()
	IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser()
	int counter


	def "verify that that Root1-traversal notifies Enter/Exit-NodeObservers in proper order"() {
		given:
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

	def "verify that that Root2-traversal notifies Enter/Exit-NodeObservers in proper order"() {
		given:
			ICommandHook enterHook = Mock(ICommandHook)
			ICommandHook exitHook = Mock(ICommandHook)
			MyCommand enterCmd = new MyCommand(enterHook)
			MyCommand exitCmd = new MyCommand(exitHook)

		when:
			treeNodeCV.setTreeNode(tdp.getRoot2());
			tt.getEnterNodeNotifier().registerObserver(enterCmd)
			tt.getExitNodeNotifier().registerObserver(exitCmd)
			tt.execute();

		then:
			1 * enterHook.testHook(0, tdp.getRoot2().getDomainObject())
		then:
			1 * enterHook.testHook(1, tdp.getRoot2Child1().getDomainObject())
		then:
			1 * exitHook.testHook(2, tdp.getRoot2Child1().getDomainObject())
		then:
			1 * enterHook.testHook(3, tdp.getRoot2Child2().getDomainObject())
		then:
			1 * exitHook.testHook(4, tdp.getRoot2Child2().getDomainObject())
		then:
			1 * exitHook.testHook(5, tdp.getRoot2().getDomainObject())
			0 * _._

	}

	def "verify that handler is properly notified during traversal"() {
		given:
			ICommand rootDomainObjectHandler = Mock(ICommand)
			ICommand oneDomainObjectHandler = Mock(ICommand)
			ICommand anotherDomainObjectHandler = Mock(ICommand)

			Map<Object, ICommand> map = new HashMap<Object, ICommand>();
			map.put(RootDomainObject.class, rootDomainObjectHandler);
			map.put(OneDomainObject.class, oneDomainObjectHandler);
			map.put(AnotherDomainObject.class, anotherDomainObjectHandler);

			MapBasedHandlerProvider hp = new MapBasedHandlerProvider();
			hp.setHandlerMap(map);
			ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
			tt.getEnterNodeNotifier().registerObserver(cmd);

			treeNodeCV.setTreeNode(tdp.getRoot1());

		when:
			tt.execute()

		then:
			1 * rootDomainObjectHandler.execute()
		then:
			1 * oneDomainObjectHandler.execute()
		then:
			1 * anotherDomainObjectHandler.execute()

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
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
			IMySpy enterSpy = Mock(IMySpy)
			IMySpy exitSpy = Mock(IMySpy)
			MyCommand enterCmd = new MyCommand(enterSpy)
			MyCommand exitCmd = new MyCommand(exitSpy)

		when:
			treeNodeCV.setTreeNode(tdp.getRoot1());
			tt.getEnterNodeNotifier().registerObserver(enterCmd)
			tt.getExitNodeNotifier().registerObserver(exitCmd)
			tt.execute();

		then:
			1 * enterSpy.testHook(0, tdp.getRoot1().getDomainObject())
		then:
			1 * enterSpy.testHook(1, tdp.getRoot1Child().getDomainObject())
		then:
			1 * enterSpy.testHook(2, tdp.getRoot1ChildChild().getDomainObject())
		then:
			1 * exitSpy.testHook(3, tdp.getRoot1ChildChild().getDomainObject())
		then:
			1 * exitSpy.testHook(4, tdp.getRoot1Child().getDomainObject())
		then:
			1 * exitSpy.testHook(5, tdp.getRoot1().getDomainObject())
			0 * _._

	}

	public class IMySpy {
		public void testHook(int position, Object aDomainObject) {}
	}

	class MyCommand implements ICommand {
		IMySpy spy;
		MyCommand(IMySpy aSpy) {
			spy = aSpy
		}

		@Override
		public void execute() {
			spy.testHook(counter, treeNodeCV.getDomainObject());
			counter++;
		}

		// necessary for inspection by mocks:
		protected void testHook(int position, Object aDomainObject) {
		}
	}

}
package org.xcommand.datastructure.tree.domainobject;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.handlerprovider.MapBasedHandlerProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

import java.util.HashMap;

class DomainObjectTreeNodeTest {

	private final TestDataProvider tdp = new TestDataProvider();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	private final NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
	private int counter = 0;

	@Test
	void verify_that_that_Root1_traversal_notifies_Enter_Exit_NodeObservers_in_proper_order() {
		ICommandHook enterHook = Mockito.mock(ICommandHook.class);
		ICommandHook exitHook = Mockito.mock(ICommandHook.class);
		MyCommand enterCmd = new MyCommand(enterHook);
		MyCommand exitCmd = new MyCommand(exitHook);
		treeNodeCV.setTreeNode(tdp.getRoot1());
		tt.getEnterNodeNotifier().registerObserver(enterCmd);
		tt.getExitNodeNotifier().registerObserver(exitCmd);

		tt.execute();

		Mockito.verify(enterHook, Mockito.times(1)).testHook(0, tdp.getRoot1().getDomainObject());
		Mockito.verify(enterHook, Mockito.times(1)).testHook(1, tdp.getRoot1Child().getDomainObject());
		Mockito.verify(enterHook, Mockito.times(1)).testHook(2, tdp.getRoot1ChildChild().getDomainObject());
		Mockito.verify(exitHook, Mockito.times(1)).testHook(3, tdp.getRoot1ChildChild().getDomainObject());
		Mockito.verify(exitHook, Mockito.times(1)).testHook(4, tdp.getRoot1Child().getDomainObject());
		Mockito.verify(exitHook, Mockito.times(1)).testHook(5, tdp.getRoot1().getDomainObject());

	}

	@Test
	void verify_that_that_Root2_traversal_notifies_Enter_Exit_NodeObservers_in_proper_order() {
		ICommandHook enterHook = Mockito.mock(ICommandHook.class);
		ICommandHook exitHook = Mockito.mock(ICommandHook.class);
		MyCommand enterCmd = new MyCommand(enterHook);
		MyCommand exitCmd = new MyCommand(exitHook);
		treeNodeCV.setTreeNode(tdp.getRoot2());;
		tt.getEnterNodeNotifier().registerObserver(enterCmd);
		tt.getExitNodeNotifier().registerObserver(exitCmd);

		tt.execute();

		Mockito.verify(enterHook, Mockito.times(1)).testHook(0, tdp.getRoot2().getDomainObject());
		Mockito.verify(enterHook, Mockito.times(1)).testHook(1, tdp.getRoot2Child1().getDomainObject());
		Mockito.verify(exitHook, Mockito.times(1)).testHook(2, tdp.getRoot2Child1().getDomainObject());
		Mockito.verify(enterHook, Mockito.times(1)).testHook(3, tdp.getRoot2Child2().getDomainObject());
		Mockito.verify(exitHook, Mockito.times(1)).testHook(4, tdp.getRoot2Child2().getDomainObject());
		Mockito.verify(exitHook, Mockito.times(1)).testHook(5, tdp.getRoot2().getDomainObject());
	}

	@Test
	void verify_that_handler_is_properly_notified_during_traversal() {
		ICommand rootDomainObjectHandler = Mockito.mock(ICommand.class);
		ICommand oneDomainObjectHandler = Mockito.mock(ICommand.class);
		ICommand anotherDomainObjectHandler = Mockito.mock(ICommand.class);

		var map = new HashMap<Object, ICommand>();
		map.put(TestDataProvider.RootDomainObject.class, rootDomainObjectHandler);
		map.put(TestDataProvider.OneDomainObject.class, oneDomainObjectHandler);
		map.put(TestDataProvider.AnotherDomainObject.class, anotherDomainObjectHandler);

		MapBasedHandlerProvider hp = new MapBasedHandlerProvider(map);
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		treeNodeCV.setTreeNode(tdp.getRoot1());

		tt.execute();

		Mockito.verify(rootDomainObjectHandler, Mockito.times(1)).execute();
		Mockito.verify(oneDomainObjectHandler, Mockito.times(1)).execute();
		Mockito.verify(anotherDomainObjectHandler, Mockito.times(1)).execute();
	}

	private static class ICommandHook {
		void testHook(@SuppressWarnings("unused") int position, @SuppressWarnings("unused") Object aDomainObject) {}
	}

	private class MyCommand implements ICommand {
		ICommandHook commandHook;
		MyCommand(ICommandHook aCommandHook) {
			commandHook = aCommandHook;
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


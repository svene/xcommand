package org.xcommand.datastructure.tree.domainobject;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.MapBasedHandlerProvider;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

import java.util.HashMap;
import java.util.Map;

public class DomainObjectTreeNodeTest
{
	private NotifyingTreeNodeTraverser tt;
	int counter;
	private ICommandHook enterHook;
	private ICommandHook exitHook;

	private TestDataProvider tdp = new TestDataProvider();

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ITreeNodeCV treeNodeCV;

	@BeforeMethod
	public void initialize() {
		treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);

		tt = new NotifyingTreeNodeTraverser();
		counter = 0;
		enterHook = Mockito.mock(ICommandHook.class);
		exitHook = Mockito.mock(ICommandHook.class);
		tt.getEnterNodeNotifier().registerObserver(new MyCommand(enterHook));
		tt.getExitNodeNotifier().registerObserver(new MyCommand(exitHook));
	}

	@Test
	public void makeSureThatRoot1TraversalNotifiesEnterExitNodeObserversInProperOrder()
	{
		treeNodeCV.setTreeNode(tdp.getRoot1());
		tt.execute();

		org.mockito.InOrder inOrder = Mockito.inOrder(enterHook, exitHook);
		inOrder.verify(enterHook).executeWithArguments(0, tdp.getRoot1().getDomainObject());
		inOrder.verify(enterHook).executeWithArguments(1, tdp.getRoot1Child().getDomainObject());
		inOrder.verify(enterHook).executeWithArguments(2, tdp.getRoot1ChildChild().getDomainObject());
		inOrder.verify(exitHook).executeWithArguments(3, tdp.getRoot1ChildChild().getDomainObject());
		inOrder.verify(exitHook).executeWithArguments(4, tdp.getRoot1Child().getDomainObject());
		inOrder.verify(exitHook).executeWithArguments(5, tdp.getRoot1().getDomainObject());
	}

	@Test public void makeSureThatRoot2TraversalNotifiesEnterExitNodeObserversInProperOrder()
	{
		treeNodeCV.setTreeNode(tdp.getRoot2());
		tt.execute();

		org.mockito.InOrder inOrder = Mockito.inOrder(enterHook, exitHook);
		inOrder.verify(enterHook).executeWithArguments(0, tdp.getRoot2().getDomainObject());
		inOrder.verify(enterHook).executeWithArguments(1, tdp.getRoot2Child1().getDomainObject());
		inOrder.verify(exitHook).executeWithArguments(2, tdp.getRoot2Child1().getDomainObject());
		inOrder.verify(enterHook).executeWithArguments(3, tdp.getRoot2Child2().getDomainObject());
		inOrder.verify(exitHook).executeWithArguments(4, tdp.getRoot2Child2().getDomainObject());
		inOrder.verify(exitHook).executeWithArguments(5, tdp.getRoot2().getDomainObject());
	}

	@Test public void testWithHandlers()
	{
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		ICommand rootDomainObjectHandler = Mockito.mock(ICommand.class);
		ICommand oneDomainObjectHandler = Mockito.mock(ICommand.class);
		ICommand anotherDomainObjectHandler = Mockito.mock(ICommand.class);

		Map<Object, ICommand> map = new HashMap<Object, ICommand>();
		map.put(TestDataProvider.RootDomainObject.class, rootDomainObjectHandler);
		map.put(TestDataProvider.OneDomainObject.class, oneDomainObjectHandler);
		map.put(TestDataProvider.AnotherDomainObject.class, anotherDomainObjectHandler);

		MapBasedHandlerProvider hp = new MapBasedHandlerProvider();
		hp.setHandlerMap(map);
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		treeNodeCV.setTreeNode(tdp.getRoot1());

		tt.execute();
		org.mockito.InOrder inOrder = Mockito.inOrder(rootDomainObjectHandler, oneDomainObjectHandler, anotherDomainObjectHandler);
		inOrder.verify(rootDomainObjectHandler).execute();
		inOrder.verify(oneDomainObjectHandler).execute();
		inOrder.verify(anotherDomainObjectHandler).execute();
	}

	public interface ICommandHook {
		// necessary for inspection by mocks:
		public void executeWithArguments(int position, Object aDomainObject);
	}

	// ICommand class with a counter as a workaround for not fully functional InOrder support of Mockito (see http://krkadev.blogspot.com/2010/02/mockito-vs-mockachino.html)
	private class MyCommand implements ICommand {
		ICommandHook commandHook;

		private MyCommand(ICommandHook aCommandHook) {
			commandHook = aCommandHook;
		}

		@Override
		public void execute() {
			// Pass context state as arguments to 'commandHook.executeWithArguments()' so that they can be verified
			// by the mocking-fw. Just with 'execute()' this is not possible since we cannot access the context from the verification:
			commandHook.executeWithArguments(counter, treeNodeCV.getDomainObject());
			counter++;
		}
	}


}

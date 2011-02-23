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
import org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject;
import org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject;
import org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainObjectTreeNodeTest
{
	private NotifyingTreeNodeTraverser tt;
	private List enterList;
	private MyCommand enterCmdSpy;
	private MyCommand exitCmdSpy;

	@BeforeMethod
	public void initialize() {
		treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);

		tt = new NotifyingTreeNodeTraverser();
		enterList = new ArrayList();
		enterCmdSpy = Mockito.spy(new MyCommand(enterList));
		exitCmdSpy = Mockito.spy(new MyCommand(enterList));
		tt.getEnterNodeNotifier().registerObserver(enterCmdSpy);
		tt.getExitNodeNotifier().registerObserver(exitCmdSpy);
	}

	@Test
	public void testEnterExitNodeTraversal()
	{
		treeNodeCV.setTreeNode(tdp.getRoot1());
		tt.execute();

		org.mockito.InOrder inOrder = Mockito.inOrder(enterCmdSpy, exitCmdSpy);
		inOrder.verify(enterCmdSpy).testHook(0, tdp.getRoot1().getDomainObject());
		inOrder.verify(enterCmdSpy).testHook(1, tdp.getRoot1Child().getDomainObject());
		inOrder.verify(enterCmdSpy).testHook(2, tdp.getRoot1ChildChild().getDomainObject());
		inOrder.verify(exitCmdSpy).testHook(3, tdp.getRoot1ChildChild().getDomainObject());
		inOrder.verify(exitCmdSpy).testHook(4, tdp.getRoot1Child().getDomainObject());
		inOrder.verify(exitCmdSpy).testHook(5, tdp.getRoot1().getDomainObject());
	}

	@Test public void testEnterExitNodeTraversal2()
	{
		treeNodeCV.setTreeNode(tdp.getRoot2());
		tt.execute();

		org.mockito.InOrder inOrder = Mockito.inOrder(enterCmdSpy, exitCmdSpy);
		inOrder.verify(enterCmdSpy).testHook(0, tdp.getRoot2().getDomainObject());
		inOrder.verify(enterCmdSpy).testHook(1, tdp.getRoot2Child1().getDomainObject());
		inOrder.verify(exitCmdSpy).testHook(2, tdp.getRoot2Child1().getDomainObject());
		inOrder.verify(enterCmdSpy).testHook(3, tdp.getRoot2Child2().getDomainObject());
		inOrder.verify(exitCmdSpy).testHook(4, tdp.getRoot2Child2().getDomainObject());
		inOrder.verify(exitCmdSpy).testHook(5, tdp.getRoot2().getDomainObject());
	}

	@Test public void testWithHandlers()
	{
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		ICommand rootDomainObjectHandler = Mockito.mock(ICommand.class);
		ICommand oneDomainObjectHandler = Mockito.mock(ICommand.class);
		ICommand anotherDomainObjectHandler = Mockito.mock(ICommand.class);

		Map<Object, ICommand> map = new HashMap<Object, ICommand>();
		map.put(RootDomainObject.class, rootDomainObjectHandler);
		map.put(OneDomainObject.class, oneDomainObjectHandler);
		map.put(AnotherDomainObject.class, anotherDomainObjectHandler);

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

	TestDataProvider tdp = new TestDataProvider();

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ITreeNodeCV treeNodeCV;

	// ICommand class with list as workaround for not fully functional InOrder support of Mockito (see http://krkadev.blogspot.com/2010/02/mockito-vs-mockachino.html)
	private class MyCommand implements ICommand {
		List list;

		private MyCommand(List aList) {
			list = aList;
		}

		@Override
		public void execute() {
			testHook(list.size(), treeNodeCV.getDomainObject());
		}

		// necessary for inspection by mocks:
		protected void testHook(int position, Object aDomainObject) {
			list.add(aDomainObject);
		}
	}


}

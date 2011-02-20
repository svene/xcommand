package org.xcommand.datastructure.tree.domainobject;

import org.junit.Test;
import org.mockito.Mockito;
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
import org.xcommand.misc.IMessageCommandCV;
import org.xcommand.misc.MessageCommand;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DomainObjectTreeNodeTest
{
	@Test public void testEnterExitNodeTraversal()
	{
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(enterCmd);
		tt.getExitNodeNotifier().registerObserver(exitCmd);

		List<String> lst = new ArrayList<String>();
//		Map ctx = new HashMap();
		messageCommandCV.setList(lst);
		messageCommandCV.setPrintWriter(new PrintWriter(System.out));
		treeNodeCV.setTreeNode(tdp.getRoot1());
		tt.execute();
		assertTrue(lst.size() == 6);
		assertNotNull(lst.get(0));
		assertNotNull(lst.get(1));
		assertNotNull(lst.get(2));
		assertNotNull(lst.get(3));
		assertNotNull(lst.get(4));
		assertNotNull(lst.get(5));

		String s0 = "TreeNode with DomainObject: org.xcommand.datastructure.tree.domainobject.domain.";
		String s = "entering " + s0;
		assertTrue(lst.get(0).startsWith(s + "RootDomainObject@"));
		assertTrue(lst.get(1).startsWith(s + "OneDomainObject@"));
		assertTrue(lst.get(2).startsWith(s + "AnotherDomainObject@"));
		s = "leaving " + s0;
		assertTrue(lst.get(3).startsWith(s + "AnotherDomainObject@"));
		assertTrue(lst.get(4).startsWith(s + "OneDomainObject@"));
		assertTrue(lst.get(5).startsWith(s + "RootDomainObject@"));

		messageCommandCV.setList(lst = new ArrayList<String>());
		messageCommandCV.setPrintWriter(new PrintWriter(System.out));
		treeNodeCV.setTreeNode(tdp.getRoot2());
		tt.execute();

		assertTrue(lst.size() == 6);
		assertNotNull(lst.get(0));
		assertNotNull(lst.get(1));
		assertNotNull(lst.get(2));
		assertNotNull(lst.get(3));
		assertNotNull(lst.get(4));
		assertNotNull(lst.get(5));

		String s1 = "entering " + s0;
		assertTrue(lst.get(0).startsWith(s1 + "RootDomainObject@"));
		assertTrue(lst.get(1).startsWith(s1 + "OneDomainObject@"));
		String s2 = "leaving " + s0;
		assertTrue(lst.get(2).startsWith(s2 + "OneDomainObject@"));
		assertTrue(lst.get(3).startsWith(s1 + "AnotherDomainObject@"));
		assertTrue(lst.get(4).startsWith(s2 + "AnotherDomainObject@"));
		assertTrue(lst.get(5).startsWith(s2 + "RootDomainObject@"));
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
		List<String> lst = new ArrayList<String>();
		messageCommandCV.setList(lst);
		messageCommandCV.setPrintWriter(new PrintWriter(System.out));

		tt.execute();
		org.mockito.InOrder inOrder = Mockito.inOrder(rootDomainObjectHandler, oneDomainObjectHandler, anotherDomainObjectHandler);
		inOrder.verify(rootDomainObjectHandler).execute();
		inOrder.verify(oneDomainObjectHandler).execute();
		inOrder.verify(anotherDomainObjectHandler).execute();
	}


	TestDataProvider tdp = new TestDataProvider();

	ICommand enterCmd = new MessageCommand()
	{
		public String getMessage()
		{
			return "entering TreeNode with DomainObject: " + treeNodeCV.getTreeNode().getDomainObject();
		}
	};
	ICommand exitCmd = new MessageCommand()
	{
		public String getMessage()
		{
			return "leaving TreeNode with DomainObject: " + treeNodeCV.getTreeNode().getDomainObject();
		}

	};
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
	private ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);

}

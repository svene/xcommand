package org.xcommand.datastructure.tree.domainobject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.*;
import org.xcommand.misc.MessageCommand;
import org.xcommand.misc.IMessageCommandCV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

		String s = "entering TreeNode with DomainObject: ";
		assertTrue(lst.get(0).startsWith(s + "org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject@"));
		assertTrue(lst.get(1).startsWith(s + "org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject@"));
		assertTrue(lst.get(2).startsWith(s + "org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject@"));
		s = "leaving TreeNode with DomainObject: ";
		assertTrue(lst.get(3).startsWith(s + "org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject@"));
		assertTrue(lst.get(4).startsWith(s + "org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject@"));
		assertTrue(lst.get(5).startsWith(s + "org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject@"));

		//System.out.println("===");
		lst = new ArrayList<String>();
//		ctx = new HashMap();
		messageCommandCV.setList(lst);
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

		String s1 = "entering TreeNode with DomainObject: ";
		assertTrue(lst.get(0).startsWith(s1 + "org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject@"));
		assertTrue(lst.get(1).startsWith(s1 + "org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject@"));
		String s2 = "leaving TreeNode with DomainObject: ";
		assertTrue(lst.get(2).startsWith(s2 + "org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject@"));
		assertTrue(lst.get(3).startsWith(s1 + "org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject@"));
		assertTrue(lst.get(4).startsWith(s2 + "org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject@"));
		assertTrue(lst.get(5).startsWith(s2 + "org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject@"));
	}

	@Test public void testWithHandlers()
	{
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(new DomainObjectTreeNodeTestHandlerProvider());
		tt.getEnterNodeNotifier().registerObserver(cmd);

//		Map ctx = new HashMap();
		treeNodeCV.setTreeNode(tdp.getRoot1());
		List<String> lst = new ArrayList<String>();
		messageCommandCV.setList(lst);
		messageCommandCV.setPrintWriter(new PrintWriter(System.out));

		tt.execute();
		assertEquals("handling RootDomainObject", lst.get(0));
		assertEquals("handling OneDomainObject", lst.get(1));
		assertEquals("handling AnotherDomainObject", lst.get(2));
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

package org.xcommand.datastructure.tree.specifictreenode;

import junit.framework.TestCase;
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

public class SpecificTreeNodeTest extends TestCase
{
	public void testEnterExitNodeTraversal()
	{
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(enterCmd);
		tt.getExitNodeNotifier().registerObserver(exitCmd);

		List<String> lst = new ArrayList<String>();
		messageCommandCV.setList(lst);
//		MessageCommandCV.setPrintToSystemOut(ctx, true);
		treeNodeCV.setTreeNode(tdp.getRoot1());
		tt.execute();
		assertEquals("entering TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.RootTreeNode", lst.get(0));
		assertEquals("entering TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode1", lst.get(1));
		assertEquals("entering TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode2", lst.get(2));
		assertEquals("leaving TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode2", lst.get(3));
		assertEquals("leaving TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode1", lst.get(4));
		assertEquals("leaving TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.RootTreeNode", lst.get(5));

//		System.out.println("===");
		lst = new ArrayList<String>();
//		ctx = new HashMap();
		messageCommandCV.setList(lst);
//		MessageCommandCV.setPrintToSystemOut(ctx, true);
		treeNodeCV.setTreeNode(tdp.getRoot2());
		tt.execute();
		assertEquals("entering TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.RootTreeNode", lst.get(0));
		assertEquals("entering TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode1", lst.get(1));
		assertEquals("leaving TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode1", lst.get(2));
		assertEquals("entering TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode2", lst.get(3));
		assertEquals("leaving TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode2", lst.get(4));
		assertEquals("leaving TreeNode: org.xcommand.datastructure.tree.specifictreenode.domain.RootTreeNode", lst.get(5));
	}

	public void testWithHandlers()
	{
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeKeyedCommand(new SpecificTreeNodeTestHandlerProvider());
		tt.getEnterNodeNotifier().registerObserver(cmd);

		treeNodeCV.setTreeNode(tdp.getRoot1());
		List lst = new ArrayList();
		messageCommandCV.setList(lst);
		messageCommandCV.setPrintWriter(new PrintWriter(System.out));

		tt.execute();
		assertEquals("RootTreeNode.rootTreeElementRoutine()", lst.get(0));
		assertEquals("TreeNode1.treeElement1Routine()", lst.get(1));
		assertEquals("TreeNode2.treeElement2Routine()", lst.get(2));
	}

	TestDataProvider tdp = new TestDataProvider();

	ICommand enterCmd = new MessageCommand()
	{
		public String getMessage()
		{
			return "entering TreeNode: " + treeNodeCV.getTreeNode().getClass().getName();
		}
	};
	ICommand exitCmd = new MessageCommand()
	{
		public String getMessage()
		{
			return "leaving TreeNode: " + treeNodeCV.getTreeNode().getClass().getName();
		}

	};
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	private IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
}

package org.collage;

import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.collage.dom.evaluator.text.VariableNameToValueTransformer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.*;
import org.xcommand.pattern.observer.AbstractBasicNotifier;
import org.xcommand.misc.IMessageCommandCV;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DomDumperLowLevelTest
{

	@Test
	public void testDomNodeEnterExitNodeTraversal()
	{
		// Setup NotifyingTreeNodeTraverser:
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(th.enterCmd);
		tt.getExitNodeNotifier().registerObserver(th.exitCmd);

		// Use NotifyingTreeNodeTraverser:
		treeNodeCV.setTreeNode(th.rootNode);
		tt.execute();

		assertEquals(12, lst.size());
		assertEquals("entering TreeNode: org.collage.dom.ast.RootNode", lst.get(0));
		assertEquals("entering TreeNode: org.collage.dom.ast.Text", lst.get(1));
		assertEquals("leaving TreeNode: org.collage.dom.ast.Text", lst.get(2));
		assertEquals("entering TreeNode: org.collage.dom.ast.Variable", lst.get(3));
		assertEquals("leaving TreeNode: org.collage.dom.ast.Variable", lst.get(4));
		assertEquals("entering TreeNode: org.collage.dom.ast.Text", lst.get(5));
		assertEquals("leaving TreeNode: org.collage.dom.ast.Text", lst.get(6));
		assertEquals("entering TreeNode: org.collage.dom.ast.Java", lst.get(7));
		assertEquals("leaving TreeNode: org.collage.dom.ast.Java", lst.get(8));
		assertEquals("entering TreeNode: org.collage.dom.ast.Text", lst.get(9));
		assertEquals("leaving TreeNode: org.collage.dom.ast.Text", lst.get(10));
		assertEquals("leaving TreeNode: org.collage.dom.ast.RootNode", lst.get(11));
	}

	/** Writing to System.out only */
	@Test
	public void testWithHandlersAndSystemOut()
	{
		// Setup:
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		List list = new ArrayList();
		list.add(new DomObjToTextTransformer());
		list.add(new TextToStringExtractor());
		list.add(th.soutCmd);
		ListCommand lc = new ListCommand();
		lc.setCommands(list);
		hp.getTextNotifier().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToVariableTransformer());
		list.add(new VariableToVariableNameExtractor());
		list.add(new VariableNameToValueTransformer());
		list.add(th.soutCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getVariableNotifier().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToJavaTransformer());
		list.add(new JavaToStringExtractor());
		list.add(th.soutCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getJavaNotifier().registerObserver(lc);

		//TODO: think about this:
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Execution:

		// Setup dynamic data:
		treeNodeCV.setTreeNode(th.rootNode);
		TCP.getContext().put("firstname", "Uli");
		tt.execute();
	}

	/** Writing to System.out and to list to test output, using lowlevel observer registration */
	@Test
	public void testWithHandlersAndSoutAndList()
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		List list = new ArrayList();
		list.add(new DomObjToTextTransformer());
		list.add(new TextToStringExtractor());
		list.add(ddth.getTextDumper());
		list.add(th.soutCmd);
		list.add(th.lstCmd);
		ListCommand lc = new ListCommand();
		lc.setCommands(list);
		hp.getTextNotifier().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToVariableTransformer());
		list.add(new VariableToVariableNameExtractor());
		list.add(ddth.getVariableDumper());
		list.add(th.soutCmd);
		list.add(th.lstCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getVariableNotifier().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToJavaTransformer());
		list.add(new JavaToStringExtractor());
		list.add(ddth.getJavaDumper());
		list.add(th.soutCmd);
		list.add(th.lstCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getJavaNotifier().registerObserver(lc);

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Execution:
		treeNodeCV.setTreeNode(th.rootNode);

		tt.execute();

		assertEquals(5, lst.size());
		assertEquals("@@@ TEXT: 'Hallo '", lst.get(0));
		assertEquals("@@@ VARIABLE: 'firstname'", lst.get(1));
		String sExpected = "@@@ TEXT: '! Willkommen bei uns.\n'";
		assertEquals(sExpected, lst.get(2));
		assertEquals("@@@ JAVA CODE: '<?java int i = 1 ?>'", lst.get(3));
		assertEquals("@@@ TEXT: 'd\n'", lst.get(4));
	}

	/** Writing to list to test output, using DomDumpingHandlerProvider for highlevel observer registration */
	@Test
	public void testWithHandlersAndList2()
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		hp.getTextNotifier().registerObserver(newDomDumpingTextObserver(true, true));
		hp.getVariableNotifier().registerObserver(newDomDumpingVariableObserver(true, true));
		hp.getJavaNotifier().registerObserver(newDomDumpingJavaObserver(true, true));

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Execution:
		treeNodeCV.setTreeNode(th.rootNode);

		tt.execute();

		assertEquals(5, lst.size());
		assertEquals("@@@ TEXT: 'Hallo '", lst.get(0));
		assertEquals("@@@ VARIABLE: 'firstname'", lst.get(1));
		String sExpected = "@@@ TEXT: '! Willkommen bei uns.\n'";
		assertEquals(sExpected, lst.get(2));
		assertEquals("@@@ JAVA CODE: '<?java int i = 1 ?>'", lst.get(3));
		assertEquals("@@@ TEXT: 'd\n'", lst.get(4));
	}

	private ICommand newDomDumpingTextObserver(boolean aPrint, boolean aList)
	{
		ICommand cmd = ddth.newDomDumpingTextObserver();
		th.attachTestObservers((AbstractBasicNotifier) cmd, aPrint, aList);
		return cmd;
	}
	private ICommand newDomDumpingVariableObserver(boolean aPrint, boolean aList)
	{
		ICommand cmd = ddth.newDomDumpingVariableObserver();
		th.attachTestObservers((AbstractBasicNotifier) cmd, aPrint, aList);
		return cmd;
	}
	private ICommand newDomDumpingJavaObserver(boolean aPrint, boolean aList)
	{
		ICommand cmd = ddth.newDomDumpingJavaObserver();
		th.attachTestObservers((AbstractBasicNotifier) cmd, aPrint, aList);
		return cmd;
	}

// --- Implementation ---

	@Before
	public void setUp() throws Exception
	{
		th = new TestHelper();
		ddth = new DomDumpingTestHelper();

		// Setup Evaluation context:
		lst = new ArrayList();
		messageCommandCV.setList(lst);
		stringHandlerCV.setString("dummy");
	}

	TestHelper th;
	DomDumpingTestHelper ddth;
	List lst;
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}

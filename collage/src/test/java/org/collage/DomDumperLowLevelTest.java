package org.collage;

import org.collage.dom.ast.*;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.collage.dom.evaluator.text.VariableNameToValueTransformer;
import org.junit.Before;
import org.junit.Test;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;
import org.xcommand.misc.IMessageCommandCV;
import org.xcommand.pattern.observer.AbstractBasicNotifier;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class DomDumperLowLevelTest
{

	@Before
	public void setUp() throws Exception
	{
		th = new TestHelper();
		ddth = new DomDumpingTestHelper();

		// Setup Evaluation context:
		lst = new ArrayList<String>();
		messageCommandCV.setList(lst);
		stringHandlerCV.setString("dummy");
	}

	@Test
	public void testParsedFile()
	{
		ITreeNode p = th.rootNode;
		assertTrue(p instanceof RootNode);
		{
			assertEquals(5, p.getChildren().size());

			verifyChildNode(p, 0, Text.class);
			verifyChildNode(p, 1, Variable.class);
			verifyChildNode(p, 2, Text.class);
			verifyChildNode(p, 3, Java.class);
			verifyChildNode(p, 4, Text.class);
		}
	}

	/**
	 * Verify that child 'aIdx' of 'aParentNode' is of type 'aChildClass' and has no children.
	 */
	private void verifyChildNode(ITreeNode aParentNode, int aIdx, Class<?> aChildClass) {
		final ITreeNode child = aParentNode.getChildren().get(aIdx);
		assertThat(child.getDomainObject(), instanceOf(aChildClass));
		assertEquals(0, child.getChildren().size());
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

	TestHelper th;
	DomDumpingTestHelper ddth;
	List<String> lst;
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}

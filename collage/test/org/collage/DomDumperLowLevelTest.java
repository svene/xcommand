package org.collage;

import junit.framework.TestCase;
import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.TextToStringExtractor;
import org.collage.dom.evaluator.common.VariableToVariableNameExtractor;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.collage.dom.evaluator.text.VariableNameToValueTransformer;
import org.xcommand.core.IXCommand;
import org.xcommand.core.ListCommand;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCV;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;
import org.xcommand.misc.MessageCommandCV;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomDumperLowLevelTest extends TestCase
{

	public void testDomNodeEnterExitNodeTraversal()
	{
		// Setup NotifyingTreeNodeTraverser:
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(th.enterCmd);
		tt.getExitNodeNotifier().registerObserver(th.exitCmd);

		// Use NotifyingTreeNodeTraverser:
		TreeNodeCV.setTreeNode(ctx, th.rootNode);
		tt.execute(ctx);

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
		hp.getTextSubject().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToVariableTransformer());
		list.add(new VariableToVariableNameExtractor());
		list.add(new VariableNameToValueTransformer());
		list.add(th.soutCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getVariableSubject().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToJavaTransformer());
		list.add(new JavaToStringExtractor());
		list.add(th.soutCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getJavaSubject().registerObserver(lc);

		//TODO: think about this:
		IXCommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Execution:

		// Setup dynamic data:
		TreeNodeCV.setTreeNode(ctx, th.rootNode);
		ctx.put("firstname", "Uli");
		tt.execute(ctx);
	}

	/** Writing to System.out and to list to test output, using lowlevel observer registration */
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
		hp.getTextSubject().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToVariableTransformer());
		list.add(new VariableToVariableNameExtractor());
		list.add(ddth.getVariableDumper());
		list.add(th.soutCmd);
		list.add(th.lstCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getVariableSubject().registerObserver(lc);

		list = new ArrayList();
		list.add(new DomObjToJavaTransformer());
		list.add(new JavaToStringExtractor());
		list.add(ddth.getJavaDumper());
		list.add(th.soutCmd);
		list.add(th.lstCmd);
		lc = new ListCommand();
		lc.setCommands(list);
		hp.getJavaSubject().registerObserver(lc);

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		IXCommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Execution:
		TreeNodeCV.setTreeNode(ctx, th.rootNode);

		tt.execute(ctx);

		assertEquals(5, lst.size());
		assertEquals("@@@ TEXT: 'Hallo '", lst.get(0));
		assertEquals("@@@ VARIABLE: 'firstname'", lst.get(1));
		String sExpected = "@@@ TEXT: '! Willkommen bei uns.\n'";
		assertEquals(sExpected, lst.get(2));
		assertEquals("@@@ JAVA CODE: '<?java int i = 1 ?>'", lst.get(3));
		assertEquals("@@@ TEXT: 'd\n'", lst.get(4));
	}

	/** Writing to list to test output, using DomDumpingHandlerProvider for highlevel observer registration */
	public void testWithHandlersAndList2()
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		hp.getTextSubject().registerObserver(newDomDumpingTextObserver(true, true));
		hp.getVariableSubject().registerObserver(newDomDumpingVariableObserver(true, true));
		hp.getJavaSubject().registerObserver(newDomDumpingJavaObserver(true, true));

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		IXCommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Execution:
		TreeNodeCV.setTreeNode(ctx, th.rootNode);

		tt.execute(ctx);

		assertEquals(5, lst.size());
		assertEquals("@@@ TEXT: 'Hallo '", lst.get(0));
		assertEquals("@@@ VARIABLE: 'firstname'", lst.get(1));
		String sExpected = "@@@ TEXT: '! Willkommen bei uns.\n'";
		assertEquals(sExpected, lst.get(2));
		assertEquals("@@@ JAVA CODE: '<?java int i = 1 ?>'", lst.get(3));
		assertEquals("@@@ TEXT: 'd\n'", lst.get(4));
	}

	private IXCommand newDomDumpingTextObserver(boolean aPrint, boolean aList)
	{
		IXCommand cmd = ddth.newDomDumpingTextObserver();
		th.attachTestObservers((SubjectImpl) cmd, aPrint, aList);
		return cmd;
	}
	private IXCommand newDomDumpingVariableObserver(boolean aPrint, boolean aList)
	{
		IXCommand cmd = ddth.newDomDumpingVariableObserver();
		th.attachTestObservers((SubjectImpl) cmd, aPrint, aList);
		return cmd;
	}
	private IXCommand newDomDumpingJavaObserver(boolean aPrint, boolean aList)
	{
		IXCommand cmd = ddth.newDomDumpingJavaObserver();
		th.attachTestObservers((SubjectImpl) cmd, aPrint, aList);
		return cmd;
	}

// --- Implementation ---

	protected void setUp() throws Exception
	{
		th = new TestHelper();
		ddth = new DomDumpingTestHelper();

		// Setup Evaluation context:
		ctx = new HashMap();
		lst = new ArrayList();
		MessageCommandCV.setList(ctx, lst);
		StringHandlerCV.setString(ctx, "dummy");
	}

	TestHelper th;
	DomDumpingTestHelper ddth;
	Map ctx;
	List lst;
}

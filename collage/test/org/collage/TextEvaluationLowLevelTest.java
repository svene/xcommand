package org.collage;

import junit.framework.TestCase;
import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.evaluator.text.TextHandlerProvider;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.TCP;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.datastructure.tree.*;
import org.xcommand.pattern.observer.AbstractBasicNotifier;
import org.xcommand.misc.IMessageCommandCV;

import java.util.ArrayList;
import java.util.List;

public class TextEvaluationLowLevelTest extends TestCase
{

	public void test1() throws Exception
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		hp.getTextNotifier().registerObserver(newTextObserver(true, true));
		hp.getVariableNotifier().registerObserver(newVariableObserver(true, true));
		hp.getJavaNotifier().registerObserver(newJavaObserver(true, true));

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		//TODO: think about this:
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// === Execution === :
		// Setup dynamic data:
		treeNodeCV.setTreeNode(th.rootNode);
		TCP.getContext().put("firstname", "Uli");

		// Execute evalutation:
		tt.execute();

		assertEquals(5, lst.size());
		assertEquals("Hallo ", lst.get(0));
		assertEquals("Uli", lst.get(1));
		String sExpected = "! Willkommen bei uns.\n";
		assertEquals(sExpected, lst.get(2));
		assertEquals("<?java int i = 1 ?>", lst.get(3));
		assertEquals("d\n", lst.get(4));
	}

// --- Implementation ---

	private ICommand newTextObserver(boolean aPrint, boolean aList)
	{
		ICommand cmd = new TextHandlerProvider().newTextObserver();
		th.attachTestObservers((AbstractBasicNotifier) cmd, aPrint, aList);
		return cmd;
	}
	private ICommand newVariableObserver(boolean aPrint, boolean aList)
	{
		ICommand cmd = new TextHandlerProvider().newVariableObserver();
		th.attachTestObservers((AbstractBasicNotifier) cmd, aPrint, aList);
		return cmd;
	}
	private ICommand newJavaObserver(boolean aPrint, boolean aList)
	{
		ICommand cmd = new TextHandlerProvider().newJavaObserver();
		th.attachTestObservers((AbstractBasicNotifier) cmd, aPrint, aList);
		return cmd;
	}

	protected void setUp() throws Exception
	{
		th = new TestHelper();

		// Setup Evaluation context:
		lst = new ArrayList();
		messageCommandCV.setList(lst);
		stringHandlerCV.setString("dummy");
	}

	TestHelper th;
	List lst;
	private DynaBeanProvider dbp = new DynaBeanProvider();
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.getBeanForInterface(ITreeNodeCV.class);
	IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.getBeanForInterface(IMessageCommandCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.getBeanForInterface(IStringHandlerCV.class);
}

package org.collage;

import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.text.TextHandlerProvider;
import org.junit.Before;
import org.junit.Test;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;
import org.xcommand.misc.IMessageCommandCV;
import org.xcommand.pattern.observer.AbstractBasicNotifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TextEvaluationLowLevelTest
{

	@Test
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

	@Before
	public void setUp() throws Exception
	{
		th = new TestHelper();

		// Setup Evaluation context:
		lst = new ArrayList();
		messageCommandCV.setList(lst);
		stringHandlerCV.setString("dummy");
	}

	TestHelper th;
	List lst;
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}

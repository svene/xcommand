package org.collage;

import junit.framework.TestCase;
import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.text.TextHandlerProvider;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCV;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;
import org.xcommand.misc.MessageCommandCV;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextEvaluationLowLevelTest extends TestCase
{

	public void test1() throws Exception
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		hp.getTextSubject().registerObserver(newTextObserver(true, true));
		hp.getVariableSubject().registerObserver(newVariableObserver(true, true));
		hp.getJavaSubject().registerObserver(newJavaObserver(true, true));

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		//TODO: think about this:
		IXCommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// === Execution === :
		// Setup dynamic data:
		TreeNodeCV.setTreeNode(ctx, th.rootNode);
		ctx.put("firstname", "Uli");

		// Execute evalutation:
		tt.execute(ctx);

		assertEquals(5, lst.size());
		assertEquals("Hallo ", lst.get(0));
		assertEquals("Uli", lst.get(1));
		String sExpected = "! Willkommen bei uns.\n";
		assertEquals(sExpected, lst.get(2));
		assertEquals("<?java int i = 1 ?>", lst.get(3));
		assertEquals("d\n", lst.get(4));
	}

// --- Implementation ---

	private IXCommand newTextObserver(boolean aPrint, boolean aList)
	{
		IXCommand cmd = new TextHandlerProvider().newTextObserver();
		th.attachTestObservers((SubjectImpl) cmd, aPrint, aList);
		return cmd;
	}
	private IXCommand newVariableObserver(boolean aPrint, boolean aList)
	{
		IXCommand cmd = new TextHandlerProvider().newVariableObserver();
		th.attachTestObservers((SubjectImpl) cmd, aPrint, aList);
		return cmd;
	}
	private IXCommand newJavaObserver(boolean aPrint, boolean aList)
	{
		IXCommand cmd = new TextHandlerProvider().newJavaObserver();
		th.attachTestObservers((SubjectImpl) cmd, aPrint, aList);
		return cmd;
	}

	protected void setUp() throws Exception
	{
		th = new TestHelper();

		// Setup Evaluation context:
		ctx = new HashMap();
		lst = new ArrayList();
		MessageCommandCV.setList(ctx, lst);
		StringHandlerCV.setString(ctx, "dummy");
	}

	TestHelper th;
	Map ctx;
	List lst;
}

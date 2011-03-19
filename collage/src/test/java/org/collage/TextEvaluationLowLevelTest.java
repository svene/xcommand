package org.collage;

import org.collage.dom.ast.DomEventHandlerProvider;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

public class TextEvaluationLowLevelTest
{

	@Test
	public void test1() throws Exception
	{
		// Setup:
		IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
		ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);

		DomEventHandlerProvider hp = new DomEventHandlerProvider();

		final TC.IStringMockHook textMockHook, variableMockHook, javaMockHook;

		hp.getTextNotifier().registerObserver(new TC.TextMockHookCommand(textMockHook = Mockito.mock(TC.IStringMockHook.class), treeNodeCV));
		hp.getVariableNotifier().registerObserver(new TC.VariableValueMockHookCommand(variableMockHook = Mockito.mock(TC.IStringMockHook.class), treeNodeCV));
		hp.getJavaNotifier().registerObserver(new TC.JavaMockHookCommand(javaMockHook = Mockito.mock(TC.IStringMockHook.class), treeNodeCV));

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		//TODO: think about this:
		tt.getEnterNodeNotifier().registerObserver(TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp));

		// Setup dynamic data:
		treeNodeCV.setTreeNode(new TestHelper().rootNode);
		TCP.getContext().put("firstname", "Uli");

		// Execution:
		tt.execute();

		// Verification:
		Mockito.verify(textMockHook, Mockito.times(3)).hookRoutineForMockVerification(Mockito.anyString());
		Mockito.verify(variableMockHook, Mockito.times(1)).hookRoutineForMockVerification(Mockito.anyString());
		Mockito.verify(javaMockHook, Mockito.times(1)).hookRoutineForMockVerification(Mockito.anyString());

		final InOrder inOrder = Mockito.inOrder(textMockHook, variableMockHook, javaMockHook);
		inOrder.verify(textMockHook).hookRoutineForMockVerification("Hallo ");
		inOrder.verify(variableMockHook).hookRoutineForMockVerification("Uli");
		inOrder.verify(textMockHook).hookRoutineForMockVerification("! Willkommen bei uns.\n");
		inOrder.verify(javaMockHook).hookRoutineForMockVerification(" int i = 1 ");
		inOrder.verify(textMockHook).hookRoutineForMockVerification("d\n");
	}

}

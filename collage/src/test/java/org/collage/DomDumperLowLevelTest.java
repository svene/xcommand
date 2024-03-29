package org.collage;

import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.*;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.collage.dom.evaluator.text.VariableNameToValueTransformer;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

public class DomDumperLowLevelTest {

	@Test
	public void testWithHandlersUsingLowlevelObserverRegistration() {
		// Setup:
		var dbp = DynaBeanProvider.newThreadClassMethodInstance();
		var treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
		var hp = new DomEventHandlerProvider();

		var textMockHook = Mockito.mock(TC.IStringMockHook.class);
		var variableMockHook = Mockito.mock(TC.IStringMockHook.class);
		var javaMockHook = Mockito.mock(TC.IStringMockHook.class);

		hp.getTextNotifier().registerObserver(new TC.TextMockHookCommand(textMockHook, treeNodeCV));
		hp.getVariableNotifier().registerObserver(new TC.VariableMockHookCommand(variableMockHook, treeNodeCV));
		hp.getJavaNotifier().registerObserver(new TC.JavaMockHookCommand(javaMockHook, treeNodeCV));

		var tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp));
		treeNodeCV.setTreeNode(new TestHelper().rootNode);

		// Execution:
		tt.execute();

		// Verification:
		Mockito.verify(textMockHook, Mockito.times(3)).hookRoutineForMockVerification(Mockito.anyString());
		Mockito.verify(variableMockHook, Mockito.times(1)).hookRoutineForMockVerification(Mockito.anyString());
		Mockito.verify(javaMockHook, Mockito.times(1)).hookRoutineForMockVerification(Mockito.anyString());

		var inOrder = Mockito.inOrder(textMockHook, variableMockHook, javaMockHook);
		inOrder.verify(textMockHook).hookRoutineForMockVerification("Hallo ");
		inOrder.verify(variableMockHook).hookRoutineForMockVerification("firstname");
		inOrder.verify(textMockHook).hookRoutineForMockVerification("! Willkommen bei uns.\n");
		inOrder.verify(javaMockHook).hookRoutineForMockVerification(" int i = 1 ");
		inOrder.verify(textMockHook).hookRoutineForMockVerification("d\n");
	}

	@Test
	public void testWithHandlersAndSystemOut() {
		// TODO: think about what we really want to test here. The difference to previous test is that here
		// we use classes like 'DomObjToTextTransformer', 'TextToStringExtractor', etc.
		// but those could be tested standalone if necessary at all.

		// Setup:
		var dbp = DynaBeanProvider.newThreadClassMethodInstance();
		var treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
		var stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
		stringHandlerCV.setString("dummy");

		var sh = Mockito.mock(IStringHandler.class);
		var shCmd = new StringHandlerCommand(sh);
		// Setup:
		var hp = new DomEventHandlerProvider();

		hp.getTextNotifier().registerObserver(new ListCommand(new DomObjToTextTransformer(), new TextToStringExtractor(), shCmd));

		hp.getVariableNotifier().registerObserver(
			new ListCommand(new DomObjToVariableTransformer(), new VariableToVariableNameExtractor(), new VariableNameToValueTransformer(), shCmd));

		hp.getJavaNotifier().registerObserver(new ListCommand(new DomObjToJavaTransformer(), new JavaToStringExtractor(), shCmd));

		var cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Setup dynamic data:
		treeNodeCV.setTreeNode(new TestHelper().rootNode);
		TCP.getContext().put("firstname", "Uli");

		// Execution:
		tt.execute();

		// Verification:
		Mockito.verify(sh, Mockito.times(5)).handleString(Mockito.any(), Mockito.any());
		var inOrder = Mockito.inOrder(sh);
		inOrder.verify(sh).handleString(Mockito.any(), Mockito.eq("Hallo "));
		inOrder.verify(sh).handleString(Mockito.any(), Mockito.eq("Uli"));
		inOrder.verify(sh).handleString(Mockito.any(), Mockito.eq("! Willkommen bei uns.\n"));
		inOrder.verify(sh).handleString(Mockito.any(), Mockito.eq("<?java int i = 1 ?>"));
		inOrder.verify(sh).handleString(Mockito.any(), Mockito.eq("d\n"));
	}

}

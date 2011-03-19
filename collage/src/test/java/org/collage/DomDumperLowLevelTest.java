package org.collage;

import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.ast.DomObjToJavaTransformer;
import org.collage.dom.ast.DomObjToTextTransformer;
import org.collage.dom.ast.DomObjToVariableTransformer;
import org.collage.dom.evaluator.common.*;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.collage.dom.evaluator.text.VariableNameToValueTransformer;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

import java.util.Arrays;
import java.util.Map;

public class DomDumperLowLevelTest
{

	@Test
	public void testWithHandlersUsingLowlevelObserverRegistration()
	{
		// Setup:
		IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
		final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
		DomEventHandlerProvider hp = new DomEventHandlerProvider();

		final TC.IStringMockHook textMockHook, variableMockHook, javaMockHook;

		hp.getTextNotifier().registerObserver(new TC.TextMockHookCommand(textMockHook = Mockito.mock(TC.IStringMockHook.class), treeNodeCV));
		hp.getVariableNotifier().registerObserver(new TC.VariableMockHookCommand(variableMockHook = Mockito.mock(TC.IStringMockHook.class), treeNodeCV));
		hp.getJavaNotifier().registerObserver(new TC.JavaMockHookCommand(javaMockHook = Mockito.mock(TC.IStringMockHook.class), treeNodeCV));

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp));
		treeNodeCV.setTreeNode(new TestHelper().rootNode);

		// Execution:
		tt.execute();

		// Verification:
		Mockito.verify(textMockHook, Mockito.times(3)).hookRoutineForMockVerification(Mockito.anyString());
		Mockito.verify(variableMockHook, Mockito.times(1)).hookRoutineForMockVerification(Mockito.anyString());
		Mockito.verify(javaMockHook, Mockito.times(1)).hookRoutineForMockVerification(Mockito.anyString());

		final InOrder inOrder = Mockito.inOrder(textMockHook, variableMockHook, javaMockHook);
		inOrder.verify(textMockHook).hookRoutineForMockVerification("Hallo ");
		inOrder.verify(variableMockHook).hookRoutineForMockVerification("firstname");
		inOrder.verify(textMockHook).hookRoutineForMockVerification("! Willkommen bei uns.\n");
		inOrder.verify(javaMockHook).hookRoutineForMockVerification(" int i = 1 ");
		inOrder.verify(textMockHook).hookRoutineForMockVerification("d\n");
	}

	@Test
	public void testWithHandlersAndSystemOut()
	{
		// TODO: think about what we really want to test here. The difference to previous test is that here
		// we use classes like 'DomObjToTextTransformer', 'TextToStringExtractor', etc.
		// but those could be tested standalone if necessary at all.

		// Setup:
		IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
		final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
		IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
		stringHandlerCV.setString("dummy");

		IStringHandler sh = Mockito.mock(IStringHandler.class);
		StringHandlerCommand shCmd = new StringHandlerCommand(sh);
		// Setup:
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		DomEventHandlerProvider hp = new DomEventHandlerProvider();

		hp.getTextNotifier().registerObserver(new ListCommand(
			Arrays.asList(new DomObjToTextTransformer(), new TextToStringExtractor(), shCmd)));

		hp.getVariableNotifier().registerObserver(new ListCommand(
			Arrays.asList(new DomObjToVariableTransformer(), new VariableToVariableNameExtractor(), new VariableNameToValueTransformer(), shCmd)));

		hp.getJavaNotifier().registerObserver(new ListCommand(
			Arrays.asList(new DomObjToJavaTransformer(), new JavaToStringExtractor(), shCmd)));

		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Setup dynamic data:
		treeNodeCV.setTreeNode(new TestHelper().rootNode);
		TCP.getContext().put("firstname", "Uli");

		// Execution:
		tt.execute();

		// Verification:
		Mockito.verify(sh, Mockito.times(5)).handleString(Mockito.<Map>any(), Mockito.<String>any());
		final InOrder inOrder = Mockito.inOrder(sh);
		inOrder.verify(sh).handleString(Mockito.<Map>any(), Mockito.eq("Hallo "));
		inOrder.verify(sh).handleString(Mockito.<Map>any(), Mockito.eq("Uli"));
		inOrder.verify(sh).handleString(Mockito.<Map>any(), Mockito.eq("! Willkommen bei uns.\n"));
		inOrder.verify(sh).handleString(Mockito.<Map>any(), Mockito.eq("<?java int i = 1 ?>"));
		inOrder.verify(sh).handleString(Mockito.<Map>any(), Mockito.eq("d\n"));
	}

}

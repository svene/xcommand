package org.collage;

import org.collage.dom.ast.*;
import org.collage.dom.evaluator.common.*;
import org.collage.dom.evaluator.text.JavaToStringExtractor;
import org.collage.dom.evaluator.text.VariableNameToValueTransformer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

import java.util.Arrays;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class DomDumperLowLevelTest
{

	private TestHelper th;

	@Before
	public void setUp() throws Exception
	{
		th = new TestHelper();
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

	@Test
	public void testWithHandlersUsingLowlevelObserverRegistration()
	{
		// Setup:
		IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
		final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
		DomEventHandlerProvider hp = new DomEventHandlerProvider();

		final IStringMockHook textMockHook = Mockito.mock(IStringMockHook.class);
		hp.getTextNotifier().registerObserver(new TextMockHookCommand(textMockHook, treeNodeCV));

		final IStringMockHook variableMockHook = Mockito.mock(IStringMockHook.class);
		hp.getVariableNotifier().registerObserver(new VariableMockHookCommand(variableMockHook, treeNodeCV));

		final IStringMockHook javaMockHook = Mockito.mock(IStringMockHook.class);
		hp.getJavaNotifier().registerObserver(new JavaMockHookCommand(javaMockHook, treeNodeCV));

		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		tt.getEnterNodeNotifier().registerObserver(TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp));
		treeNodeCV.setTreeNode(th.rootNode);

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
		treeNodeCV.setTreeNode(th.rootNode);
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




// --- Implementation ---

	private interface IStringMockHook {
		void hookRoutineForMockVerification(String aString);
	}

	private static abstract class AbstractMockHookCommand implements ICommand {
		protected final IStringMockHook stringMockHook;
		protected final ITreeNodeCV treeNodeCV;
		public AbstractMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			stringMockHook = aStringMockHook;
			treeNodeCV = aTreeNodeCV;
		}
	}
	private static class TextMockHookCommand extends AbstractMockHookCommand {
		public TextMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			super(aStringMockHook, aTreeNodeCV);
		}
		@Override
		public void execute() {
			final Text text = (Text) treeNodeCV.getTreeNode().getDomainObject(); // in case of ClassCastException the test fails which is what we want.
			stringMockHook.hookRoutineForMockVerification(text.getValue());
		}
	}
	private static class VariableMockHookCommand extends AbstractMockHookCommand {
		public VariableMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			super(aStringMockHook, aTreeNodeCV);
		}
		@Override
		public void execute() {
			final Variable v = (Variable) treeNodeCV.getTreeNode().getDomainObject(); // in case of ClassCastException the test fails which is what we want.
			stringMockHook.hookRoutineForMockVerification(v.getVariableName());
		}
	}
	private static class JavaMockHookCommand extends AbstractMockHookCommand {
		public JavaMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			super(aStringMockHook, aTreeNodeCV);
		}
		@Override
		public void execute() {
			final Java java = (Java) treeNodeCV.getTreeNode().getDomainObject(); // in case of ClassCastException the test fails which is what we want.
			stringMockHook.hookRoutineForMockVerification(java.getValue());
		}
	}

}

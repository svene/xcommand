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
import org.xcommand.misc.IMessageCommandCV;
import org.xcommand.pattern.observer.AbstractBasicNotifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

	@Test
	public void testWithHandlersAndSystemOut()
	{
		StringHandlerCommand dbg = new StringHandlerCommand(new IStringHandler() {
			@Override
			public void handleString(Map aCtx, String aString) {
				System.out.printf("'*%s*'", aString);
			}
		});

		IStringHandler sh = Mockito.mock(IStringHandler.class);
		StringHandlerCommand shCmd = new StringHandlerCommand(sh);
		// Setup:
		NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
		DomEventHandlerProvider hp = new DomEventHandlerProvider();

		hp.getTextNotifier().registerObserver(new ListCommand(
			Arrays.asList(new DomObjToTextTransformer(), new TextToStringExtractor(), dbg, shCmd)));

		hp.getVariableNotifier().registerObserver(new ListCommand(
			Arrays.asList(new DomObjToVariableTransformer(), new VariableToVariableNameExtractor(), new VariableNameToValueTransformer(), dbg, shCmd)));

		hp.getJavaNotifier().registerObserver(new ListCommand(
			Arrays.asList(new DomObjToJavaTransformer(), new JavaToStringExtractor(), dbg, shCmd)));

		//TODO: think about this:
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		tt.getEnterNodeNotifier().registerObserver(cmd);

		// Setup dynamic data:
		_treeNodeCV.setTreeNode(th.rootNode);
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
		_treeNodeCV.setTreeNode(th.rootNode);

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
	ITreeNodeCV _treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}

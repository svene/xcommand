package org.collage;

import org.collage.dom.ast.Java;
import org.collage.dom.ast.Text;
import org.collage.dom.ast.Variable;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class TC {
	static abstract class AbstractMockHookCommand implements ICommand {
		protected final IStringMockHook stringMockHook;
		protected final ITreeNodeCV treeNodeCV;
		public AbstractMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			stringMockHook = aStringMockHook;
			treeNodeCV = aTreeNodeCV;
		}
	}

	static class TextMockHookCommand extends AbstractMockHookCommand {
		public TextMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			super(aStringMockHook, aTreeNodeCV);
		}
		@Override
		public void execute() {
			final Text text = (Text) treeNodeCV.getTreeNode().getDomainObject(); // in case of ClassCastException the test fails which is what we want.
			stringMockHook.hookRoutineForMockVerification(text.getValue());
		}
	}

	static class VariableMockHookCommand extends AbstractMockHookCommand {
		public VariableMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			super(aStringMockHook, aTreeNodeCV);
		}
		@Override
		public void execute() {
			final Variable v = (Variable) treeNodeCV.getTreeNode().getDomainObject(); // in case of ClassCastException the test fails which is what we want.
			stringMockHook.hookRoutineForMockVerification(v.getVariableName());
		}
	}
	static class VariableValueMockHookCommand extends AbstractMockHookCommand {
		public VariableValueMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			super(aStringMockHook, aTreeNodeCV);
		}
		@Override
		public void execute() {
			final Variable v = (Variable) treeNodeCV.getTreeNode().getDomainObject(); // in case of ClassCastException the test fails which is what we want.
			stringMockHook.hookRoutineForMockVerification((String) TCP.getContext().get(v.getVariableName()));
		}
	}

	static class JavaMockHookCommand extends AbstractMockHookCommand {
		public JavaMockHookCommand(IStringMockHook aStringMockHook, ITreeNodeCV aTreeNodeCV) {
			super(aStringMockHook, aTreeNodeCV);
		}
		@Override
		public void execute() {
			final Java java = (Java) treeNodeCV.getTreeNode().getDomainObject(); // in case of ClassCastException the test fails which is what we want.
			stringMockHook.hookRoutineForMockVerification(java.getValue());
		}
	}

	interface IStringMockHook {
		void hookRoutineForMockVerification(String aString);
	}
}

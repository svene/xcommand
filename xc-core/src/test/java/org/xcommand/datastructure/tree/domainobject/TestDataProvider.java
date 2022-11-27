package org.xcommand.datastructure.tree.domainobject;

import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.ITreeNode;

public class TestDataProvider {

	public ITreeNode getRoot1() {
		return root1;
	}

	public ITreeNode getRoot1Child() {
		return root1.getChildren().get(0);
	}

	public ITreeNode getRoot1ChildChild() {
		return getRoot1Child().getChildren().get(0);
	}

	public ITreeNode getRoot2() {
		return root2;
	}

	public ITreeNode getRoot2Child1() {
		return root2.getChildren().get(0);
	}

	public ITreeNode getRoot2Child2() {
		return root2.getChildren().get(1);
	}

// --- Implementation ---

	private final ITreeNode root1;
	private final ITreeNode root2;

	{

		// Setup element structure to test:
		root1 = new TreeNode(new RootDomainObject());
		TreeNode te1 = new TreeNode(new OneDomainObject());
		TreeBuilder tb = new TreeBuilder();
		tb.addChild(root1, te1);

		TreeNode te2 = new TreeNode(new AnotherDomainObject());
		tb.addChild(te1, te2);

		root2 = new TreeNode(new RootDomainObject());
		TreeNode te3 = new TreeNode(new OneDomainObject());
		tb.addChild(root2, te3);

		TreeNode te4 = new TreeNode(new AnotherDomainObject());
		tb.addChild(root2, te4);
	}

	public static class OneDomainObject {
	}

	public static class AnotherDomainObject {
	}

	public static class RootDomainObject {
	}
}

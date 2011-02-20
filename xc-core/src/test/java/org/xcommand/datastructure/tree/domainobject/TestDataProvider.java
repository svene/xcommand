package org.xcommand.datastructure.tree.domainobject;

import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.domainobject.domain.*;

public class TestDataProvider
{

// --- Access ---

	public ITreeNode getRoot1()
	{
		return root1;
	}

	public ITreeNode getRoot1Child() {
		return root1.getChildren().get(0);
	}
	public ITreeNode getRoot1ChildChild() {
		return getRoot1Child().getChildren().get(0);
	}

	public ITreeNode getRoot2()
	{
		return root2;
	}
	public ITreeNode getRoot2Child1() {
		return root2.getChildren().get(0);
	}
	public ITreeNode getRoot2Child2() {
		return root2.getChildren().get(1);
	}

// --- Implementation ---

	private ITreeNode root1;
	private ITreeNode root2;

	{
		TreeBuilder tb = new TreeBuilder();

		// Setup element structure to test:
		root1 = new TreeNode();
		root1.setDomainObject(new RootDomainObject());

		TreeNode te1 = new TreeNode();
		te1.setDomainObject(new OneDomainObject());
		tb.addChild(root1, te1);

		TreeNode te2 = new TreeNode();
		te2.setDomainObject(new AnotherDomainObject());
		tb.addChild(te1, te2);

		root2 = new TreeNode(); root2.setDomainObject(new RootDomainObject());
		te1 = new TreeNode(); te1.setDomainObject(new OneDomainObject());
		tb.addChild(root2, te1);

		te2 = new TreeNode(); te2.setDomainObject(new AnotherDomainObject());
		tb.addChild(root2, te2);
	}

}

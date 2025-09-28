package org.xcommand.datastructure.tree.specifictreenode;

import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNode;

public class TestDataProvider {
    public TreeNode getRoot1() {
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

    public static class RootTreeNode extends TreeNode {
        public RootTreeNode(Object domainObject) {
            super(domainObject);
        }
    }

    public static class TreeNode2 extends TreeNode {
        public TreeNode2(Object domainObject) {
            super(domainObject);
        }
    }

    public static class TreeNode1 extends TreeNode {
        public TreeNode1(Object domainObject) {
            super(domainObject);
        }
    }

    // --- Implementation ---

    private final TreeNode root1;
    private final ITreeNode root2;

    {
        /*
         root1/te1/te2

         root2/te1
         root2/te2

        */

        // Setup element structure to test:
        root1 = new RootTreeNode(null);
        TreeNode1 te1 = new TreeNode1(null);
        TreeBuilder tb = new TreeBuilder();
        tb.addChild(root1, te1);
        tb.addChild(te1, new TreeNode2(null));

        root2 = new RootTreeNode(null);
        tb.addChild(root2, new TreeNode1(null));
        tb.addChild(root2, new TreeNode2(null));
    }
}

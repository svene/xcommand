package org.xcommand.datastructure.tree;

/**
 * Helper class to construct Tree structures
 */
public class TreeBuilder {
	public void addChild(ITreeNode aParent, ITreeNode aChild) {
		aParent.getChildren().add(aChild);
	}
}

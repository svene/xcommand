package org.collage;

import junit.framework.Assert;
import org.collage.dom.ast.Java;
import org.collage.dom.ast.RootNode;
import org.collage.dom.ast.Text;
import org.collage.dom.ast.Variable;
import org.junit.Test;
import org.xcommand.datastructure.tree.ITreeNode;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class TestHelperTest {

	@Test
	public void	verifyThatParsedFileCreateProperTreeStructure() {
		ITreeNode p = new TestHelper().rootNode;
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
		Assert.assertEquals(0, child.getChildren().size());
	}

}

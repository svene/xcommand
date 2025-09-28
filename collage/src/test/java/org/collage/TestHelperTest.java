package org.collage;

import static org.assertj.core.api.Assertions.assertThat;

import org.collage.dom.ast.Java;
import org.collage.dom.ast.RootNode;
import org.collage.dom.ast.Text;
import org.collage.dom.ast.Variable;
import org.junit.jupiter.api.Test;
import org.xcommand.datastructure.tree.ITreeNode;

public class TestHelperTest {

    @Test
    public void verifyThatParsedFileCreateProperTreeStructure() {
        ITreeNode p = new TestHelper().rootNode;
        assertThat(p instanceof RootNode).isTrue();
        assertThat(p.getChildren()).hasSize(5);

        verifyChildNode(p, 0, Text.class);
        verifyChildNode(p, 1, Variable.class);
        verifyChildNode(p, 2, Text.class);
        verifyChildNode(p, 3, Java.class);
        verifyChildNode(p, 4, Text.class);
    }

    /**
     * Verify that child 'aIdx' of 'aParentNode' is of type 'aChildClass' and has no children.
     */
    private void verifyChildNode(ITreeNode aParentNode, int aIdx, Class<?> aChildClass) {
        var child = aParentNode.getChildren().get(aIdx);
        assertThat(child.getDomainObject()).isInstanceOf(aChildClass);
        assertThat(child.getChildren()).isEmpty();
    }
}

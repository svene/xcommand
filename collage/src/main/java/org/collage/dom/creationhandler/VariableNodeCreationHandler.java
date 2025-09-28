package org.collage.dom.creationhandler;

import org.collage.dom.ast.Variable;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.template.parser.IParserCV;

public class VariableNodeCreationHandler implements ICommand {
    @Override
    public void execute() {
        var s = domNodeCreationHandlerCV.getValue();
        trace("got VARIABLE: '" + s + "'");
        var v = new Variable();
        v.setVariableName(s);
        var node = new TreeNode(v);
        tb.addChild(treeNodeCV.getTreeNode(), node);
    }

    private void trace(String aString) {
        var ps = parserCV.getTraceStream();
        if (ps == null) {
            return;
        }
        ps.println("### " + aString);
    }

    private final TreeBuilder tb = new TreeBuilder();
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
    IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}

package org.collage.dom.creationhandler;

import lombok.extern.slf4j.Slf4j;
import org.collage.dom.ast.Variable;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.template.parser.IParserCV;

@Slf4j
public class VariableNodeCreationHandler implements ICommand {
    @Override
    public void execute() {
        var s = domNodeCreationHandlerCV.getValue();
        log.debug("got VARIABLE: '{}'", s);
        trace("got VARIABLE: '" + s + "'");
        var v = new Variable(s);
        var node = new TreeNode(v);
        tb.addChild(treeNodeCV.getTreeNode(), node);
    }

    private void trace(String aString) {
        if (!parserCV.hasTraceStream()) {
            return;
        }
        parserCV.getTraceStream().println("### " + aString);
    }

    private final TreeBuilder tb = new TreeBuilder();
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
    IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}

package org.collage.dom.creationhandler;

import org.collage.dom.ast.Text;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.*;
import org.xcommand.template.parser.IParserCV;

public class TextNodeCreationHandler implements ICommand {
    @Override
    public void execute() {
        var s = domNodeCreationHandlerCV.getValue();
        trace("got TEXT: '" + s + "'");
        var text = new Text();
        text.setValue(s);
        var node = new TreeNode(text);
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

package org.collage.dom.creationhandler;

import lombok.extern.slf4j.Slf4j;
import org.collage.dom.ast.Text;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.*;
import org.xcommand.template.parser.IParserCV;

@Slf4j
public class TextNodeCreationHandler implements ICommand {
    @Override
    public void execute() {
        var s = domNodeCreationHandlerCV.getValue();
        log.debug("got TEXT: '{}'", s);
        trace("got TEXT: '" + s + "'");
        var text = new Text(s);
        var node = new TreeNode(text);
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

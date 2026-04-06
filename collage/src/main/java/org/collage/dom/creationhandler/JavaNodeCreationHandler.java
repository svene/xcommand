package org.collage.dom.creationhandler;

import lombok.extern.slf4j.Slf4j;
import org.collage.dom.ast.Java;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.*;
import org.xcommand.template.parser.IParserCV;

@Slf4j
public class JavaNodeCreationHandler implements ICommand {
    @Override
    public void execute() {
        var s = domNodeCreationHandlerCV.getValue();
        log.debug("got JAVA CODE: '{}'", s);
        trace("got JAVA CODE: '" + s + "'");
        var node = new TreeNode(new Java(s));
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

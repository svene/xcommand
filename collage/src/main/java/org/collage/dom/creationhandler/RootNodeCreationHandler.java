package org.collage.dom.creationhandler;

import org.collage.dom.ast.RootNode;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

public class RootNodeCreationHandler implements ICommand {
    @Override
    public void execute() {
        trace("started");
        treeNodeCV.setTreeNode(new RootNode());
    }

    private void trace(String aString) {
        var ps = parserCV.getTraceStream();
        if (ps == null) {
            return;
        }
        ps.println("### " + aString);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

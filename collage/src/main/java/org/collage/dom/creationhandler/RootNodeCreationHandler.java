package org.collage.dom.creationhandler;

import lombok.extern.slf4j.Slf4j;
import org.collage.dom.ast.RootNode;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

@Slf4j
public class RootNodeCreationHandler implements ICommand {
    @Override
    public void execute() {
        log.debug("started");
        trace("started");
        treeNodeCV.setTreeNode(new RootNode());
    }

    private void trace(String aString) {
        if (!parserCV.hasTraceStream()) {
            return;
        }
        parserCV.getTraceStream().println("### " + aString);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
}

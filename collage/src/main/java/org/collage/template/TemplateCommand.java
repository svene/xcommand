package org.collage.template;

import java.io.StringWriter;
import java.io.Writer;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;

public abstract class TemplateCommand implements ICommand {

    protected TemplateCommand(ITreeNode aRootNode) {
        rootNode = aRootNode;
    }

    public Writer getWriter() {
        return writer;
    }

    protected NotifyingTreeNodeTraverser getNotifyingTreeNodeTraverser() {
        return notifyingTreeNodeTraverser;
    }

    public void setWriter(Writer aWriter) {
        writer = aWriter;
    }

    protected void setNotifyingTreeNodeTraverser(NotifyingTreeNodeTraverser aNotifyingTreeNodeTraverser) {
        notifyingTreeNodeTraverser = aNotifyingTreeNodeTraverser;
    }

    @Override
    public void execute() {
        // If `writer' is available output will be written to it. Otherwise
        // it will be written to a String, which is available via `StringHandlerCV.getString(aCtx)'
        boolean noWriter = getWriter() == null;
        StringWriter sw = null;
        if (noWriter) {
            sw = new StringWriter(1024);
            evaluationCV.setWriter(sw);
        } else {
            evaluationCV.setWriter(getWriter());
        }
        treeNodeCV.setTreeNode(rootNode);
        getNotifyingTreeNodeTraverser().execute();
        if (noWriter) {
            stringHandlerCV.setString(sw.toString());
        }
    }

    private Writer writer;
    protected ITreeNode rootNode;
    protected NotifyingTreeNodeTraverser notifyingTreeNodeTraverser;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}

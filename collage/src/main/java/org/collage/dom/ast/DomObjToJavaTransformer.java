package org.collage.dom.ast;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class DomObjToJavaTransformer implements ICommand {
    @Override
    public void execute() {
        var node = treeNodeCV.getTreeNode();
        var java = (Java) node.getDomainObject();
        javaCV.setJava(java);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
}

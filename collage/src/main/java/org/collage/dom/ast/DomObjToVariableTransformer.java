package org.collage.dom.ast;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class DomObjToVariableTransformer implements ICommand {
    @Override
    public void execute() {
        var node = treeNodeCV.getTreeNode();
        var v = (Variable) node.getDomainObject();
        variableCV.setVariable(v);
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    IVariableCV variableCV = dbp.newBeanForInterface(IVariableCV.class);
}

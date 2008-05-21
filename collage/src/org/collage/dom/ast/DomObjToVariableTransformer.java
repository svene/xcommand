package org.collage.dom.ast;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class DomObjToVariableTransformer implements ICommand
{
	public void execute()
	{
		ITreeNode node = treeNodeCV.getTreeNode();
		Variable v = (Variable) node.getDomainObject();
		variableCV.setVariable(v);
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.getBeanForInterface(ITreeNodeCV.class);
	IVariableCV variableCV = (IVariableCV) dbp.getBeanForInterface(IVariableCV.class);
}

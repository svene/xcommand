package org.collage.dom.ast;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class DomObjToVariableTransformer implements ICommand
{
	@Override
	public void execute()
	{
		ITreeNode node = treeNodeCV.getTreeNode();
		Variable v = (Variable) node.getDomainObject();
		variableCV.setVariable(v);
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	IVariableCV variableCV = dbp.newBeanForInterface(IVariableCV.class);
}

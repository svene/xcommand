package org.collage.dom.ast;

import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.util.Map;

public class DomObjToVariableTransformer implements IXCommand
{
	public void execute(Map aCtx)
	{
		ITreeNode node = TreeNodeCV.getTreeNode(aCtx);
		Variable v = (Variable) node.getDomainObject();
		VariableCV.setVariable(aCtx, v);
	}
}

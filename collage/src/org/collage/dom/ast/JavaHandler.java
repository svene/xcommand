package org.collage.dom.ast;

import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.util.Map;

public class JavaHandler implements IXCommand
{
	public void execute(Map aCtx)
	{
		ITreeNode node = TreeNodeCV.getTreeNode(aCtx);
		Java java = (Java) node.getDomainObject();
		JavaCV.setJava(aCtx, java);
	}
}

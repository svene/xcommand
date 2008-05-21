package org.collage.dom.ast;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class JavaHandler implements ICommand
{
	public void execute()
	{
		ITreeNode node = treeNodeCV.getTreeNode();
		Java java = (Java) node.getDomainObject();
		javaCV.setJava(java);
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.getBeanForInterface(ITreeNodeCV.class);
	IJavaCV javaCV = (IJavaCV) dbp.getBeanForInterface(IJavaCV.class);
}

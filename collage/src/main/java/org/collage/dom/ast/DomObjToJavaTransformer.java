package org.collage.dom.ast;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;

public class DomObjToJavaTransformer implements ICommand
{
	public void execute()
	{
		ITreeNode node = treeNodeCV.getTreeNode();
		Java java = (Java) node.getDomainObject();
		javaCV.setJava(java);
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	IJavaCV javaCV = dbp.newBeanForInterface(IJavaCV.class);
}

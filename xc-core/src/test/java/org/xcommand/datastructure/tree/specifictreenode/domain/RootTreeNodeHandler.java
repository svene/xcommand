package org.xcommand.datastructure.tree.specifictreenode.domain;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.misc.IMessageCommandCV;

import java.io.PrintWriter;
import java.util.List;

public class RootTreeNodeHandler implements ICommand
{
	public void execute()
	{
		RootTreeNode el = (RootTreeNode) treeNodeCV.getTreeNode();
		List lst = messageCommandCV.getList();
		PrintWriter w = messageCommandCV.getPrintWriter();
		el.rootTreeElementRoutine(lst, w);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	private IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
}

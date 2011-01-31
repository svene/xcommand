package org.xcommand.datastructure.tree.domainobject.domain;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.misc.IMessageCommandCV;

import java.io.PrintWriter;
import java.util.List;

public class DomainObjectHandler implements ICommand
{
	public void execute()
	{
		Object domainObject = treeNodeCV.getDomainObject();
		String s = domainObject.toString();
		List lst = messageCommandCV.getList();
		if (lst != null)
		{
			lst.add(s);
		}
		PrintWriter w = messageCommandCV.getPrintWriter();
		if (w != null)
		{
			w.println(s);
		}
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	private IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
}

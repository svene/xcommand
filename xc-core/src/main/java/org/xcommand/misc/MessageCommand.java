package org.xcommand.misc;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

import java.io.PrintWriter;
import java.util.List;

public abstract class MessageCommand implements ICommand
{
	public abstract String getMessage();

	public void execute()
	{
		String s = getMessage();
		List lst = messageCommandCV.getList();
		if (lst != null)
		{
			lst.add(s);
		}
		PrintWriter pw = messageCommandCV.getPrintWriter();
		if (pw != null)
		{
			pw.println(s);
		}
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
}
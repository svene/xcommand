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

	@Override
	public void execute()
	{
		String s = getMessage();
		List<String> lst = messageCommandCV.getList();
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
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IMessageCommandCV messageCommandCV = dbp.newBeanForInterface(IMessageCommandCV.class);
}

package org.xcommand.misc;

import org.xcommand.core.IXCommand;

import java.util.Map;
import java.util.List;
import java.io.PrintWriter;

public abstract class MessageCommand implements IXCommand
{
	public abstract String getMessage(Map aCtx);

	public void execute(Map aCtx)
	{
		String s = getMessage(aCtx);
		List lst = MessageCommandCV.getList(aCtx);
		if (lst != null)
		{
			lst.add(s);
		}
		PrintWriter pw = MessageCommandCV.getPrintWriter(aCtx);
		if (pw != null)
		{
			pw.println(s);
		}
	}
}

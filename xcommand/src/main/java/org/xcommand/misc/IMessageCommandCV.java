package org.xcommand.misc;

import java.io.PrintWriter;
import java.util.List;

public interface IMessageCommandCV
{
	public PrintWriter getPrintWriter();
	public List getList();
	public void setPrintWriter(PrintWriter aPrintWriter);
	public void setList(List aList);
}

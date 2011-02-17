package org.xcommand.misc;

import java.io.PrintWriter;
import java.util.List;

public interface IMessageCommandCV
{
	public PrintWriter getPrintWriter();
	public List<String> getList();
	public void setPrintWriter(PrintWriter aPrintWriter);
	public void setList(List<String> aList);
}

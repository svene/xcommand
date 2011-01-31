package org.xcwebapp.cmd;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.web.IWebCV;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebCmd1 implements ICommand
{
	public void execute()
	{
		System.out.println("WebCmd1.execute()");
		HttpServletResponse response = webCV.getResponse();
		try
		{
			PrintWriter pw = response.getWriter();
			pw.println("<html>");
			pw.println("<body>");
			pw.println("<p>");
			pw.println("Hi there! My name ist WebCmd1!");
			pw.println("</p>");
			pw.println("</body>");
			pw.println("</html>");
			pw.flush();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
}
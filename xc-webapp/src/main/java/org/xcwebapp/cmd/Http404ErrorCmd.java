package org.xcwebapp.cmd;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.web.IWebCV;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Http404ErrorCmd implements ICommand
{
	public void execute()
	{
		System.out.println("WebXCmd2.execute()");
		HttpServletResponse response = webCV.getResponse();
		try
		{
//			int httpErrorCode =  ResponseContextView.getHttpErrorCode(aCtx).intValue();
//			String httpErrorMessage =  ResponseContextView.getHttpErrorMessage(aCtx);
			response.sendError(404, "not found");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
}

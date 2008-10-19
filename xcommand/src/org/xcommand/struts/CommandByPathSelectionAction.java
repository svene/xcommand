package org.xcommand.struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.web.IWebCV;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class CommandByPathSelectionAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		try
		{
			TCP.pushContext(new HashMap());
			// Set web properties:
			webCV.setRequest(request);
			webCV.setResponse(response);

			// Set Struts properties:
			StaticStrutsContextView.setActionForm(form);
			StaticStrutsContextView.setActionMapping(mapping);
			webCV.setRequest(request);
			webCV.setResponse(response);

			// Get command configured with String using the path as beanname:
			String path = mapping.getPath();
			System.out.println("path = " + path);

			WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(
				getServlet().getServletContext());
			if (wac.containsBean(path))
			{
				ICommand cmd = (ICommand) wac.getBean(path);
				cmd.execute();
			}
			ActionForward forward = StaticStrutsContextView.getActionForward();
			TCP.popContext();
			return forward;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
}

package org.xcommand.struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xcommand.ICommand;
import org.xcommand.web.WebContextView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

public class CommandByPathSelectionAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		try
		{
			Map ctx = new HashMap();

			// Set web properties:
			WebContextView.setRequest(ctx, request);
			WebContextView.setResponse(ctx, response);

			// Set Struts properties:
			StaticStrutsContextView.setActionForm(ctx, form);
			StaticStrutsContextView.setActionMapping(ctx, mapping);
			WebContextView.setRequest(ctx, request);
			WebContextView.setResponse(ctx, response);

			// Get command configured with String using the path as beanname:
			String path = mapping.getPath();
			System.out.println("path = " + path);

			WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(
				getServlet().getServletContext());
			if (wac.containsBean(path))
			{
				ICommand cmd = (ICommand) wac.getBean(path);
				cmd.execute(ctx);
			}
			ActionForward forward = StaticStrutsContextView.getActionForward(ctx);
			return forward;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}

package org.xcommand.template.jst;

import org.xcommand.core.IXCommand;
import org.xcommand.web.WebContextView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class JavaTemplateCmd implements IXCommand
{

	public void execute(Map aCtx)
	{
		try
		{
			aCtx.put("writer", WebContextView.getResponse(aCtx).getWriter());

			// Find classname for current request-URI:
			HttpServletRequest request = WebContextView.getRequest(aCtx);
			String contextPath = request.getContextPath();
			System.out.println("contextPath = " + contextPath);
			String uri = request.getRequestURI();
			int idx = uri.indexOf(contextPath);
			if (idx == -1) System.out.println(contextPath + " not found in request uri: " + uri);
			uri = uri.substring(idx + contextPath.length() + 1);
			System.out.println("uri = " + uri);
			String className = uriToClassnameMapper.getClassnameForUri(uri);

			// Get object for classname:
			className = className.replace('.', '/');
			IXCommand cmd = (IXCommand) JSTJaninoObjectCreator.newObject(aCtx, className);

			// Execute command:
			cmd.execute(aCtx);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	public void setJaninoObjectCreator2(JSTJaninoObjectCreator aJSTJaninoObjectCreator)
	{
		JSTJaninoObjectCreator = aJSTJaninoObjectCreator;
	}

	public void setUriToClassnameMapper(IUriToClassnameMapper aUriToClassnameMapper)
	{
		uriToClassnameMapper = aUriToClassnameMapper;
	}

	private IUriToClassnameMapper uriToClassnameMapper;
	private JSTJaninoObjectCreator JSTJaninoObjectCreator;
}

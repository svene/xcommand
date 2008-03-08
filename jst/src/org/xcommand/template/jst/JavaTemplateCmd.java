package org.xcommand.template.jst;

import org.xcommand.core.INXCommand;
import org.xcommand.core.TCP;
import org.xcommand.web.WebSCV;

import javax.servlet.http.HttpServletRequest;

public class JavaTemplateCmd implements INXCommand
{

	public void execute()
	{
		try
		{
			TCP.getContext().put("writer", WebSCV.getResponse().getWriter());

			// Find classname for current request-URI:
			HttpServletRequest request = WebSCV.getRequest();
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
			INXCommand cmd = (INXCommand) jstJaninoObjectCreator.newObject(TCP.getContext(), className);

			// Execute command:
			cmd.execute();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	public void setJaninoObjectCreator2(JSTJaninoObjectCreator aJSTJaninoObjectCreator)
	{
		jstJaninoObjectCreator = aJSTJaninoObjectCreator;
	}

	public void setUriToClassnameMapper(IUriToClassnameMapper aUriToClassnameMapper)
	{
		uriToClassnameMapper = aUriToClassnameMapper;
	}

	private IUriToClassnameMapper uriToClassnameMapper;
	private JSTJaninoObjectCreator jstJaninoObjectCreator;
}
package org.xcommand.template.jst;

import org.xcommand.core.*;
import org.xcommand.web.IWebCV;

import javax.servlet.http.HttpServletRequest;

public class JavaTemplateCmd implements ICommand
{

	public void execute()
	{
		try
		{
			TCP.getContext().put("writer", webCV.getResponse().getWriter());

			// Find classname for current request-URI:
			HttpServletRequest request = webCV.getRequest();
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
			ICommand cmd = (ICommand) jstJaninoObjectCreator.newObject(className);

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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
}
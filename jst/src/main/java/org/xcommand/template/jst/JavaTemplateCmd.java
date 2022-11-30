package org.xcommand.template.jst;

import org.xcommand.core.*;
import org.xcommand.web.IWebCV;

import jakarta.servlet.http.HttpServletRequest;

public class JavaTemplateCmd implements ICommand {

	@Override
	public void execute() {
		try {
			TCP.getContext().put("writer", webCV.getResponse().getWriter());

			// Find classname for current request-URI:
			var request = webCV.getRequest();
			var contextPath = request.getContextPath();
			System.out.println("contextPath = " + contextPath);
			var uri = request.getRequestURI();
			var idx = uri.indexOf(contextPath);
			if (idx == -1) {
				System.out.println(contextPath + " not found in request uri: " + uri);
			}
			uri = uri.substring(idx + contextPath.length() + 1);
			System.out.println("uri = " + uri);
			var className = uriToClassnameMapper.getClassnameForUri(uri);

			// Get object for classname:
			className = className.replace('.', '/');
			var cmd = (ICommand) jstJaninoObjectCreator.newObject(className);

			// Execute command:
			cmd.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setJaninoObjectCreator2(JSTJaninoObjectCreator aJSTJaninoObjectCreator) {
		jstJaninoObjectCreator = aJSTJaninoObjectCreator;
	}

	public void setUriToClassnameMapper(IUriToClassnameMapper aUriToClassnameMapper) {
		uriToClassnameMapper = aUriToClassnameMapper;
	}

	private IUriToClassnameMapper uriToClassnameMapper;
	private JSTJaninoObjectCreator jstJaninoObjectCreator;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}

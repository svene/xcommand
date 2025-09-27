package org.xcommand.template.jst;

import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Sneaky;
import org.xcommand.core.*;
import org.xcommand.web.IWebCV;

@Slf4j
public class JavaTemplateCmd implements ICommand {

	@Override
	public void execute() {
		TCP.getContext().put(
			"writer",
			Sneaky.supplier(() -> webCV.getResponse().getWriter()).get()
		);

		// Find classname for current request-URI:
		var request = webCV.getRequest();
		var contextPath = request.getContextPath();
		log.debug("contextPath = " + contextPath);
		var uri = request.getRequestURI();
		var idx = uri.indexOf(contextPath);
		if (idx == -1) {
			log.info(contextPath + " not found in request uri: " + uri);
		}
		uri = uri.substring(idx + contextPath.length() + 1);
		log.debug("uri = " + uri);
		var className = uriToClassnameMapper.getClassnameForUri(uri);

		// Get object for classname:
		className = className.replace('.', '/');
		var cmd = (ICommand) jstJaninoObjectCreator.newObject(className);

		// Execute command:
		cmd.execute();
	}

	public void setJaninoObjectCreator2(JSTJaninoObjectCreator aJSTJaninoObjectCreator) {
		jstJaninoObjectCreator = aJSTJaninoObjectCreator;
	}

	public void setUriToClassnameMapper(IUriToClassnameMapper aUriToClassnameMapper) {
		uriToClassnameMapper = aUriToClassnameMapper;
	}

	private IUriToClassnameMapper uriToClassnameMapper = in -> in;
	private JSTJaninoObjectCreator jstJaninoObjectCreator = new JSTJaninoObjectCreator();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}

package org.xcommand.tool.vcgenerator;

import java.util.List;

public class WebCVMetaCV extends MetaCV
{
	public WebCVMetaCV()
	{
		super();
		setPackageName("org.xcommand.web");
		setClassName("WebCV");

		List props = getProperties();
		props.add(new MetaCVProperty("request", javax.servlet.http.HttpServletRequest.class));
		props.add(new MetaCVProperty("response", javax.servlet.http.HttpServletResponse.class));
		props.add(new MetaCVProperty("servletContext", javax.servlet.ServletContext.class));
	}
}

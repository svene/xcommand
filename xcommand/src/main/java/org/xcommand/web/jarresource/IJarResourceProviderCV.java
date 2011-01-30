package org.xcommand.web.jarresource;

import org.springframework.core.io.Resource;

public interface IJarResourceProviderCV
{
	public String getResourceName();
	public Resource getResource();
	public Long getLastModified();
	public void setResourceName(String aResourceName);
	public void setResource(Resource aResource);
	public void setLastModified(Long aLastModified);
}

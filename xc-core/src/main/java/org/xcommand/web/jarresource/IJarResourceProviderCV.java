package org.xcommand.web.jarresource;

import org.springframework.core.io.Resource;

public interface IJarResourceProviderCV {
    String getResourceName();

    Resource getResource();

    Long getLastModified();

    void setResourceName(String aResourceName);

    void setResource(Resource aResource);

    void setLastModified(Long aLastModified);
}

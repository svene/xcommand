package org.xcommand.web.jarresource;

import org.springframework.core.io.Resource;

import java.util.Map;

public class JarResourceProviderContextView
{

// --- Access ---

	public static String getResourceName(Map aCtx)
	{
		return (String) aCtx.get(JarResourceProviderContextView.KEY_RESOURCE_NAME);
	}

	public static Resource getResource(Map aCtx)
	{
		return (Resource) aCtx.get(JarResourceProviderContextView.KEY_RESOURCE);
	}

	public static Long getLastModified(Map aCtx)
	{
		return (Long) aCtx.get(JarResourceProviderContextView.KEY_RESOURCE_LAST_MODIFIED);
	}

// --- Setting ---

	public static void setResourceName(Map aCtx, String aResourceName)
	{
		aCtx.put(JarResourceProviderContextView.KEY_RESOURCE_NAME, aResourceName);
	}

	public static void setResource(Map aCtx, Resource aResource)
	{
		aCtx.put(JarResourceProviderContextView.KEY_RESOURCE, aResource);
	}

	public static void setLastModified(Map aCtx, Long aLastModified)
	{
		aCtx.put(JarResourceProviderContextView.KEY_RESOURCE_LAST_MODIFIED, aLastModified);
	}

// --- Implementation ---

	public static final String NS = "org.xcommand.web.jarresource.JarResourceProviderContextView.";
	public static final String KEY_RESOURCE_NAME = JarResourceProviderContextView.NS + "RESOURCE_NAME";
	public static final String KEY_RESOURCE = JarResourceProviderContextView.NS + "RESOURCE";
	public static final String KEY_RESOURCE_LAST_MODIFIED = JarResourceProviderContextView.NS + "RESOURCE_LAST_MODIFIED";


}

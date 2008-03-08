package org.xcommand.web.jarresource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Map;
import java.io.File;

import org.springframework.core.io.UrlResource;
import org.springframework.web.context.support.ServletContextResource;
import org.xcommand.core.IXCommand;
import org.xcommand.web.WebXCV;

import javax.servlet.ServletContext;

/**
 * Objects providing access to resources in jar files.
 */
public class JarResourceProvider implements IXCommand
{
	/**
	 * Input:
	 *  JarResourceProviderContextView.setResourceName(<name>)
	 *    name: name of resource in classpath. E.g.: '/WPCSamples/jarresource/a/b/c/loading.gif'
	 * Output:
	 *  JarResourceProviderContextView.getResource(): Resource
	 *  JarResourceProviderContextView.getLastModified(): Long
	 */
	public void execute(Map aCtx)
	{
		System.out.println("JarResourceProvider.execute");
		String resourceName = JarResourceProviderContextView.getResourceName(aCtx);
		Resource resource = new ClassPathResource(resourceName);
		try
		{
			File f = resource.getFile();
			// No problem to get access to resource, so simply provide info about it:
			JarResourceProviderContextView.setResource(aCtx, resource);
			long lm = f.lastModified();
			JarResourceProviderContextView.setLastModified(aCtx, new Long(lm));
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			String msg = e.getMessage();
			if (msg.indexOf(WSJAR_FILE) == -1)
			{
				throw new RuntimeException("unsupported resource: " + resource.getDescription());
			}
			int i1 = msg.indexOf(WEBINF_LIB);
			if (i1 == -1)
			{
				// jar file is outside of 'WEB-INF-LIB'
				// TBD
				throw new RuntimeException("unsupported resource: " + resource.getDescription());
			}

			int i2 = msg.indexOf(BANG_SLASH, i1);
			if (i2 == -1)
			{
				throw new RuntimeException("unsupported resource: " + resource.getDescription());
			}

			String filename = msg.substring(i1, i2);
			System.out.println("servletcontext-filename='" + filename + "'");
			ServletContext sc = WebXCV.getServletContext(aCtx);
			resource = new ServletContextResource(sc, filename);
			try
			{
				File f = resource.getFile();
				long lm = f.lastModified();
				JarResourceProviderContextView.setLastModified(aCtx, new Long(lm));

				String s = msg.substring(msg.indexOf(WSJAR_FILE));
				resource = new UrlResource(s);
				JarResourceProviderContextView.setResource(aCtx, resource);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private static final String WSJAR_FILE = "wsjar:file:/";
	private static final String WEBINF_LIB = "/WEB-INF/lib";
	private static final String BANG_SLASH = "!/";

}

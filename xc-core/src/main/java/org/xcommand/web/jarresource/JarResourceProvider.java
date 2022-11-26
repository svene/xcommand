package org.xcommand.web.jarresource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.context.support.ServletContextResource;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.web.IWebCV;

import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/**
 * Objects providing access to resources in jar files.
 */
public class JarResourceProvider implements ICommand
{
	/**
	 * Input:
	 *  JarResourceProviderContextView.setResourceName(<name>)
	 *    name: name of resource in classpath. E.g.: '/WPCSamples/jarresource/a/b/c/loading.gif'
	 * Output:
	 *  JarResourceProviderContextView.getResource(): Resource
	 *  JarResourceProviderContextView.getLastModified(): Long
	 */
	@Override
	public void execute()
	{
		System.out.println("JarResourceProvider.execute");
		String resourceName = jarResourceProviderCV.getResourceName();
		Resource resource = new ClassPathResource(resourceName);
		try
		{
			File f = resource.getFile();
			// No problem to get access to resource, so simply provide info about it:
			jarResourceProviderCV.setResource(resource);
			long lm = f.lastModified();
			jarResourceProviderCV.setLastModified(lm);
		} catch (IOException | RuntimeException e)
		{
			//e.printStackTrace();
			String msg = e.getMessage();
			if (!msg.contains(WSJAR_FILE))
			{
				throw new RuntimeException("unsupported resource: " + resource.getDescription(), e);
			}
			int i1 = msg.indexOf(WEBINF_LIB);
			if (i1 == -1)
			{
				// jar file is outside of 'WEB-INF-LIB'
				// TBD
				throw new RuntimeException("unsupported resource: " + resource.getDescription(), e);
			}

			int i2 = msg.indexOf(BANG_SLASH, i1);
			if (i2 == -1)
			{
				throw new RuntimeException("unsupported resource: " + resource.getDescription(), e);
			}

			String filename = msg.substring(i1, i2);
			System.out.println("servletcontext-filename='" + filename + "'");
			ServletContext sc = webCV.getServletContext();
			resource = new ServletContextResource(sc, filename);
			try
			{
				File f = resource.getFile();
				long lm = f.lastModified();
				jarResourceProviderCV.setLastModified(lm);

				String s = msg.substring(msg.indexOf(WSJAR_FILE));
				resource = new UrlResource(s);
				jarResourceProviderCV.setResource(resource);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		}
	}

	private static final String WSJAR_FILE = "wsjar:file:/";
	private static final String WEBINF_LIB = "/WEB-INF/lib";
	private static final String BANG_SLASH = "!/";
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
	private final IJarResourceProviderCV jarResourceProviderCV = dbp.newBeanForInterface(
		IJarResourceProviderCV.class); 
}

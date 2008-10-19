package org.xcommand.web.jarresource;

import org.springframework.core.io.Resource;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.web.IWebCV;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Servlet capable of serving resources (like images, .css, .js etc) out of jar files.
 * To use this servlet create a servlet mapping with a path like '/jarresource/*' for example.
 * This will have the effect that e.g. the following URI: '/WPCSamples/jarresource/resources/a/b/c/loading.gif'
 * from the browser will cause the servlet to search the resource with this URI in the classpath and when found returns it.
 * It guesses it's mime type by using 'ServletContext.getMimeType()'. It also implements 'getLastModified()' which returns
 * the timestamp of the jar file containing the resource to support HTTP caching.
 *
 * Implementation note: uses JarResourceProvider 
 */
public class JarResourceServlet extends HttpServlet
{

	protected long getLastModified(HttpServletRequest request)
	{
		JarResourceProvider jrp = new JarResourceProvider();
		webCV.setServletContext(getServletContext());
		String resName = getResourceNameFromRequest(request);
		jarResourceProviderCV.setResourceName(resName);
		jrp.execute();
		showLastModifiedDate();
		return jarResourceProviderCV.getLastModified().longValue();
	}

	private String getResourceNameFromRequest(HttpServletRequest request)
	{
		String requestURI = request.getRequestURI();
		final String contextPath = request.getContextPath();
		System.out.println("request.getRequestURI(): " + requestURI);
		System.out.println("request.getServletPath(): " + request.getServletPath());
		System.out.println("request.getContextPath(): " + contextPath);

		final String prefix = contextPath + request.getServletPath() + "/";
		System.out.println("prefix: " + prefix);
		String resName;
		int i1 = requestURI.indexOf(prefix);
		if (i1 != -1)
		{
			resName = requestURI.substring(i1 + prefix.length());
		}
		else
		{
			resName = request.getParameter("resource");
		}
		return resName;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("JarResourceServlet.doGet(): serving content");
		String resName = jarResourceProviderCV.getResourceName();
		if (resName != null)
		{
			Resource resource = jarResourceProviderCV.getResource();
			InputStream is = resource.getInputStream();
			ServletOutputStream os = response.getOutputStream();
			String mimeType = getServletContext().getMimeType(request.getRequestURI());
			if (mimeType == null || mimeType.length() == 0)
			{
				if (resName.endsWith(".swf"))
				{
					System.out.println("** setting mimetype to: 'application/x-shockwave-flash'");
					mimeType = "application/x-shockwave-flash";
				}
			}
			System.out.println("mimeType: " + mimeType);
			response.setContentType(mimeType);

			writeInputStreamToOutputStream(is, os);
		}
		else
		{
			System.out.println("JarResourceServlet.doGet(): no resource found");
		}
	}

	/**
	 * Read all content form `a_is' and write it to `a_os'.
	 */
	private static void writeInputStreamToOutputStream(InputStream a_is, OutputStream a_os) throws IOException
	{
		byte[] buffer = new byte[4096];
		int bytes_read;
		while ((bytes_read = a_is.read(buffer)) != -1)
		{
			a_os.write(buffer, 0, bytes_read);
		}
	}

	private void showLastModifiedDate()
	{
		Resource resource = jarResourceProviderCV.getResource();
		long l = jarResourceProviderCV.getLastModified().longValue();

		System.out.println("JarResourceServlet.getLastModified(" + resource.getDescription() + "): result as date=" + new Date(l));
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
	private IJarResourceProviderCV jarResourceProviderCV = (IJarResourceProviderCV) dbp.newBeanForInterface(
		IJarResourceProviderCV.class);

}

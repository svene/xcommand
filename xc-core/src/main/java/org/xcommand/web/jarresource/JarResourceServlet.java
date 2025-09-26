package org.xcommand.web.jarresource;

import org.springframework.core.io.Resource;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.web.IWebCV;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Servlet capable of serving resources (like images, .css, .js etc) out of jar files.
 * To use this servlet create a servlet mapping with a path like '/jarresource/*' for example.
 * This will have the effect that e.g. the following URI: '/WPCSamples/jarresource/resources/a/b/c/loading.gif'
 * from the browser will cause the servlet to search the resource with this URI in the classpath and when found returns it.
 * It guesses it's mime type by using 'ServletContext.getMimeType()'. It also implements 'getLastModified()' which returns
 * the timestamp of the jar file containing the resource to support HTTP caching.
 * <p>
 * Implementation note: uses JarResourceProvider
 */
public class JarResourceServlet extends HttpServlet {

	@Override
	protected long getLastModified(HttpServletRequest request) {
		webCV.setServletContext(getServletContext());
		String resName = getResourceNameFromRequest(request);
		jarResourceProviderCV.setResourceName(resName);
		new JarResourceProvider().execute();
		showLastModifiedDate();
		return jarResourceProviderCV.getLastModified();
	}

	private String getResourceNameFromRequest(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		System.out.println("request.getRequestURI(): " + requestURI);
		System.out.println("request.getServletPath(): " + request.getServletPath());
		System.out.println("request.getContextPath(): " + contextPath);

		String prefix = contextPath + request.getServletPath() + "/";
		System.out.println("prefix: " + prefix);
		int i1 = requestURI.indexOf(prefix);
		return i1 == -1 ? request.getParameter("resource") : requestURI.substring(i1 + prefix.length());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("JarResourceServlet.doGet(): serving content");
		String resName = jarResourceProviderCV.getResourceName();
		if (resName != null) {
			Resource resource = jarResourceProviderCV.getResource();
			InputStream is = resource.getInputStream();
			ServletOutputStream os = response.getOutputStream();
			String mimeType = getServletContext().getMimeType(request.getRequestURI());
			if (mimeType == null || mimeType.length() == 0) {
				if (resName.endsWith(".swf")) {
					System.out.println("Setting mimetype to: 'application/x-shockwave-flash'");
					mimeType = "application/x-shockwave-flash";
				}
			}
			System.out.println("mimeType: " + mimeType);
			response.setContentType(mimeType);
			is.transferTo(os);
		} else {
			System.out.println("JarResourceServlet.doGet(): no resource found");
		}
	}

	private void showLastModifiedDate() {
		Resource resource = jarResourceProviderCV.getResource();
		Long l = jarResourceProviderCV.getLastModified();
		System.out.println("JarResourceServlet.getLastModified(" + resource.getDescription() + "): result: " + Instant.ofEpochMilli(l));
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
	private final IJarResourceProviderCV jarResourceProviderCV = dbp.newBeanForInterface(
		IJarResourceProviderCV.class);

}

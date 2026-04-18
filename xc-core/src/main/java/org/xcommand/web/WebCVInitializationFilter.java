package org.xcommand.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;

/**
 * Filter responsible for initializing the XC Context with webapp-related information (request, response, servletContext)
 */
public class WebCVInitializationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(WebCVInitializationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aFilterChain)
            throws IOException, ServletException {
        // Populate XC Context with webapp-related information (request, response):
        TCP.pushContext(TCP.newInheritableContext());
        webCV.setRequest((HttpServletRequest) aRequest);
        webCV.setResponse((HttpServletResponse) aResponse);
        log.debug("WebCVInitializationFilter.doFilter()");
        aFilterChain.doFilter(aRequest, aResponse);
        TCP.popContext();
    }

    @Override
    public void destroy() {}

    ServletContext servletContext;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}

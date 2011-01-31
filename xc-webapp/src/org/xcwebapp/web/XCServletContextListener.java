package org.xcwebapp.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xcommand.core.*;
import org.xcommand.web.IWebCV;
import org.xcwebapp.app.AppBeanProvider;
import org.xcwebapp.app.XcWebappCVProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class XCServletContextListener implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent aServletContextEvent)
	{
		TCP.pushContext(AC.getInstance());
		webCV.setServletContext(aServletContextEvent.getServletContext());
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] {"spring-cfg.xml"});
		cvp.getSpringAppCtxCV().setApplicationContext(ac);
		AppBeanProvider abp = (AppBeanProvider) ac.getBean("APP_BEAN_PROVIDER");
		cvp.getAppCV().setAppBeanProvider(abp);
		ICommand cmd = abp.getXcServletContextListenerCommand();
		if (cmd != null)
		{
//			cmd.execute(appCtx);
			cmd.execute();
		}
		TCP.popContext();
	}

	public void contextDestroyed(ServletContextEvent aServletContextEvent)
	{
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IWebCV webCV = (IWebCV) dbp.newBeanForInterface(IWebCV.class);
	private XcWebappCVProvider cvp = new XcWebappCVProvider();
}

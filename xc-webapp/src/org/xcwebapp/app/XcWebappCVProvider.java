package org.xcwebapp.app;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcwebapp.tmp.ISpringAppCtxCV;

public class XcWebappCVProvider
{
	public XcWebappCVProvider()
	{
		appCV = (IAppCV) dbp.newBeanForInterface(IAppCV.class);
		springAppCtxCV = (ISpringAppCtxCV) dbp.newBeanForInterface(ISpringAppCtxCV.class);
	}

	public IAppCV getAppCV()
	{
		return appCV;
	}

	public ISpringAppCtxCV getSpringAppCtxCV()
	{
		return springAppCtxCV;
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IAppCV appCV;
	private ISpringAppCtxCV springAppCtxCV;
}

package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.web.IWebCV;

import javax.servlet.ServletContext;
import java.util.*;

public class ServletContextBasedJSTProvider implements IJSTProvider
{

	public void initialize()
	{
		ServletContext sc = webCV.getServletContext();

		Set set = sc.getResourcePaths("/");
		Iterator it = set.iterator();
		while (it.hasNext())
		{
			Object o = it.next();
			System.out.println("o.class=" + o.getClass().getName() + ", value=" + o);
		}
	}

	public ClassMapEntry getClassMapEntry(Map aCtx, String aClassname)
	{
		return null;
	}


	public void setSrcDirs(List aSrcDirs)
	{
		srcDirs = aSrcDirs;
	}

	public Map getClassMap()
	{
		return classMap;
	}

	public INotifier getChangeNotifier()
	{
		return changeNotifier;
	}

	private List srcDirs;
	private final Map classMap = new HashMap();
	private final INotifier changeNotifier = new BasicNotifier();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}

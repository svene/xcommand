package org.xcommand.template.jst;

import org.xcommand.pattern.observer.ISubject;
import org.xcommand.pattern.observer.SubjectImpl;
import org.xcommand.web.WebContextView;

import javax.servlet.ServletContext;
import java.util.*;

public class ServletContextBasedJSTProvider implements IJSTProvider
{

	public void initialize(Map aCtx)
	{
		ServletContext sc = WebContextView.getServletContext(aCtx);

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

	public ISubject getChangeNotifier()
	{
		return changeNotifier;
	}

	private List srcDirs;
	private Map classMap = new HashMap();
	private ISubject changeNotifier = new SubjectImpl();
}

package org.xcommand.template.jst;

import org.xcommand.technology.janino.MapResourceFinder;
import org.xcommand.core.IXCommand;
import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.DebuggingInformation;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class JSTJaninoObjectCreator
{
// --- Initialization ---

	public void initialize()
	{
		// Put source of template into `janinoClassMap' so that Janino can work with it:
		janinoClassMap.clear();
		Iterator it = jstProvider.getClassMap().entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry me = (Map.Entry) it.next();
			ClassMapEntry cme = (ClassMapEntry) me.getValue();
			janinoClassMap.put(me.getKey(), cme.source.getBytes());
		}
		mrf = new MapResourceFinder(janinoClassMap);
	}

	public Class getClass(Map aCtx, String aClassname)
	{
		// Make sure classes are loaded:
		jstProvider.getClassMapEntry(aCtx, aClassname);
		ClassLoader parentClassLoader = getClass().getClassLoader();
		String encoding = null;
		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, mrf, encoding, DebuggingInformation.ALL);
	 	String dotClassName = aClassname.replace('/', '.');
		try
		{
			return cl.loadClass(dotClassName);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

	}
	public Object newObject(Map aCtx, String aClassname)
	{
		Class clazz = getClass(aCtx, aClassname);
		try
		{
			return clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setJstProvider(IJSTProvider aJstProvider)
	{
		jstProvider = aJstProvider;
		jstProvider.getChangeNotifier().registerObserver(new IXCommand()
		{
			public void execute(Map aCtx)
			{
				initialize();
			}
		});
	}
// --- Implementation ---

	private Map janinoClassMap = new HashMap();
	private IJSTProvider jstProvider;
	MapResourceFinder mrf;
}

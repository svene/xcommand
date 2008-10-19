package org.xcommand.template.jst;

import org.xcommand.technology.janino.MapResourceFinder;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.DebuggingInformation;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

public class JSTJaninoObjectCreator
{
// --- Initialization ---

	public void initialize()
	{
		// Put source of template into `janinoClassMap' so that Janino can work with it:
		janinoClassMap.clear();
		Map classMap = jstScannerCV.getClassMap();
		Iterator it = classMap.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry me = (Map.Entry) it.next();
			ClassMapEntry cme = (ClassMapEntry) me.getValue();
			janinoClassMap.put(me.getKey(), cme.fme.content.getBytes());
			cme.fme.lastmodified = cme.fme.file.lastModified();
		}
		mrf = new MapResourceFinder(janinoClassMap);
	}

	public Class getClass(String aClassname)
	{
		// Make sure classes are loaded:
		Map classMap = jstScannerCV.getClassMap();
		ClassMapEntry cme = (ClassMapEntry) classMap.get(aClassname);
		if (cme != null)
		{
			System.out.println("cme for '" + aClassname + "' found");
			if (cme.lastloaded > cme.fme.lastmodified)
			{
				System.out.println("loaded class still valid.");
				return cme.clazz;
			}
		}
		// Load class via Janino:
		ClassLoader parentClassLoader = getClass().getClassLoader();
		String encoding = null;
		System.out.println("loading class '" + aClassname + "'");
		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, mrf, encoding, DebuggingInformation.ALL);
	 	String dotClassName = aClassname.replace('/', '.');
		try
		{
			Class clazz = cl.loadClass(dotClassName);
			cme.clazz = clazz;
			cme.lastloaded = new Date().getTime();
			return clazz;
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

	}
	public Object newObject(String aClassname)
	{
		Class clazz = getClass(aClassname);
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
		jstProvider.getChangeNotifier().registerObserver(new ICommand()
		{
			public void execute()
			{
				initialize();
			}
		});
	}
// --- Implementation ---

	private Map janinoClassMap = new HashMap();
	private IJSTProvider jstProvider;
	MapResourceFinder mrf;
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IJSTScannerCV jstScannerCV = (IJSTScannerCV) dbp.newBeanForInterface(IJSTScannerCV.class);
}

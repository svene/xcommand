package org.xcommand.template.jst;

import org.xcommand.technology.janino.XCMapResourceFinder;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.codehaus.janino.JavaSourceClassLoader;

import java.nio.charset.StandardCharsets;
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
		var classMap = jstScannerCV.getClassMap();
		classMap.forEach((key, cme) -> {
			janinoClassMap.put(key, cme.fme.content.getBytes());
			cme.fme.lastmodified = cme.fme.file.lastModified();
		});
		mrf = new XCMapResourceFinder(janinoClassMap);
	}

	public Class<?> getClass(String aClassname)
	{
		// Make sure classes are loaded:
		var classMap = jstScannerCV.getClassMap();
		ClassMapEntry cme = classMap.get(aClassname);
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
		System.out.println("loading class '" + aClassname + "'");
		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, mrf, StandardCharsets.UTF_8.name());
	 	String dotClassName = aClassname.replace('/', '.');
		try
		{
			var clazz = cl.loadClass(dotClassName);
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
		var clazz = getClass(aClassname);
		try
		{
			return clazz.getDeclaredConstructor().newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setJstProvider(IJSTProvider aJstProvider)
	{
		aJstProvider.getChangeNotifier().registerObserver(this::initialize);
	}
// --- Implementation ---

	private final Map<String, byte[]> janinoClassMap = new HashMap<>();
	XCMapResourceFinder mrf;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
}

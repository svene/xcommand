package org.xcommand.technology.janino;

import org.codehaus.commons.compiler.util.resource.MapResourceCreator;
import org.codehaus.commons.compiler.util.resource.MapResourceFinder;
import org.codehaus.commons.compiler.util.resource.ResourceCreator;
import org.codehaus.janino.CachingJavaSourceClassLoader;


import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

public class JaninoObjectCreator
{

// --- Initialization ---

	public JaninoObjectCreator(Map aJavaSourceMap)
	{
		javaSourceMap = aJavaSourceMap;
		javaSourceResourceFinder = new XCMapResourceFinder(javaSourceMap);
		javaClassResourceFinder = new MapResourceFinder(javaClassMap);
	}

	public Class getClass(String aClassname)
	{
		ClassLoader parentClassLoader = getClass().getClassLoader();
//		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, javaSourceResourceFinder, encoding, DebuggingInformation.ALL);
		//TODO: implement caching loader:
		ResourceCreator classFileCacheResourceCreator = new MapResourceCreator(javaClassMap);
		ClassLoader cl = new CachingJavaSourceClassLoader(parentClassLoader, javaSourceResourceFinder, StandardCharsets.UTF_8.name(),
			javaClassResourceFinder, classFileCacheResourceCreator);
	 	String dotClassName = aClassname.replace('/', '.');
		Class clazz = null;
		try
		{
			clazz = cl.loadClass(dotClassName);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		return clazz;

	}
	public Object getObject(String aClassname)
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

// --- Implementation ---

	private Map javaSourceMap = new HashMap();
	private Map javaClassMap = new HashMap()
	{
		public Object get(Object key)
		{
			System.out.println("JaninoObjectCreator.get()");
			return super.get(key);
		}

		public Object put(Object key, Object value)
		{
			System.out.println("JaninoObjectCreator.put()");
			return super.put(key, value);
		}
	};
	private XCMapResourceFinder javaSourceResourceFinder;
	private MapResourceFinder javaClassResourceFinder;

}

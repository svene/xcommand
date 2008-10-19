package org.xcommand.technology.janino;

import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.DebuggingInformation;
import org.codehaus.janino.CachingJavaSourceClassLoader;
import org.codehaus.janino.util.resource.*;


import java.util.Map;
import java.util.HashMap;

public class JaninoObjectCreator
{

// --- Initialization ---

	public JaninoObjectCreator(Map aJavaSourceMap)
	{
		javaSourceMap = aJavaSourceMap;
		javaSourceResourceFinder = new MapResourceFinder(javaSourceMap);
		javaClassResourceFinder = new org.codehaus.janino.util.resource.MapResourceFinder(javaClassMap);
	}

	public Class getClass(String aClassname)
	{
		ClassLoader parentClassLoader = getClass().getClassLoader();
		String encoding = null;
//		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, javaSourceResourceFinder, encoding, DebuggingInformation.ALL);
		//TODO: implement caching loader:
		ResourceCreator classFileCacheResourceCreator = new MapResourceCreator(javaClassMap);
		ClassLoader cl = new CachingJavaSourceClassLoader(parentClassLoader, javaSourceResourceFinder, encoding,
			javaClassResourceFinder, classFileCacheResourceCreator, DebuggingInformation.ALL);
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
	private MapResourceFinder javaSourceResourceFinder;
	private org.codehaus.janino.util.resource.MapResourceFinder javaClassResourceFinder;

}

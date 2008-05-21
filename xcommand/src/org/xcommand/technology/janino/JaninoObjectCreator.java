package org.xcommand.technology.janino;

import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.DebuggingInformation;

import java.util.Map;
import java.util.HashMap;

public class JaninoObjectCreator
{

// --- Initialization ---

	public JaninoObjectCreator(Map aClassMap)
	{
		classMap = aClassMap;
		mrf = new MapResourceFinder(classMap);
	}

	public Class getClass(String aClassname)
	{
		ClassLoader parentClassLoader = getClass().getClassLoader();
		String encoding = null;
		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, mrf, encoding, DebuggingInformation.ALL);
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

	Map classMap = new HashMap();
	MapResourceFinder mrf;
}

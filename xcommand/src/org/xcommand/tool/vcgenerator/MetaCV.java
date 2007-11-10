package org.xcommand.tool.vcgenerator;

import java.util.*;

public class MetaCV
{

// --- Initialization ---

	public MetaCV()
	{
	}

// --- Access ---

	public Set getImports()
	{
		Set result = new HashSet();
		result.add("java.util.Map");
		Iterator it = getProperties().iterator();
		while (it.hasNext())
		{
			MetaCVProperty p = (MetaCVProperty) it.next();
			result.add(p.getFqType());
		}
		return result;
	}

	public List getProperties()
	{
		return properties;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public String getClassName()
	{
		return className;
	}

// --- Setting ---

	public void setPackageName(String aPackageName)
	{
		packageName = aPackageName;
	}

	public void setClassName(String aClassName)
	{
		className = aClassName;
	}

// --- Implementation ---

	private String packageName;
	private String className;
	private List/*<MetaCVProperty>*/ properties = new ArrayList();
}

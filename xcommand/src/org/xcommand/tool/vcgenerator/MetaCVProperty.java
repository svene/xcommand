package org.xcommand.tool.vcgenerator;

public class MetaCVProperty
{

// --- Initialization ---

	public MetaCVProperty(String aName, String aType)
	{
		setName(aName);
		setFqType(aType);
	}

	public MetaCVProperty(String aName, Class aClassType)
	{
		setName(aName);
		setFqType(aClassType.getName());
	}

// --- Access ---

	public String getName()
	{
		return name;
	}

	public String getFqType()
	{
		return fqType;
	}
	public String getType()
	{
		return type;
	}

// --- Setting ---

	public void setName(String aName)
	{
		name = aName;
	}

	public void setFqType(String aFqType)
	{
		fqType = aFqType;
		int p = fqType.lastIndexOf(".");
		if (p != -1)
		{
			type = fqType.substring(p + 1);
		}
		else
		{
			type = fqType;
		}
	}

// --- Implementation ---

	private String name;
	private String fqType;
	private String type;
}

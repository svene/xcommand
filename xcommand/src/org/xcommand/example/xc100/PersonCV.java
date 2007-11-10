package org.xcommand.example.xc100;

import java.util.Map;

public class PersonCV
{

// --- Initialization ---

	public PersonCV(Map aCtx)
	{
		ctx = aCtx;
	}

// --- Access ---

	public String getName()
	{
		return (String) ctx.get(KEY_NAME);
	}

// --- Setting ---

	public void setName(String aName)
	{
		ctx.put(KEY_NAME, aName);
	}

// --- Constants ---

	public static final String NS = "org.xcommand.example.xc100.PersonCV.";
	public static final String KEY_NAME = NS + "NAME";

// --- Implementation ---

	private Map ctx;
}

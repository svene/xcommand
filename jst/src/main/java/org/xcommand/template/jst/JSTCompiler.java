package org.xcommand.template.jst;

import org.xcommand.technology.janino.JaninoObjectCreator;

public class JSTCompiler
{

// --- Access ---

	public JSTSourceLoader getJstSourceLoader()
	{
		return jstSourceLoader;
	}

// --- Setting ---

	public void setJstSourceLoader(JSTSourceLoader aJstSourceLoader)
	{
		jstSourceLoader = aJstSourceLoader;
	}

// --- Processing ---

	public Object getObject(String aClassname)
	{
		// Compile parsed JST source, instatiate object and execute it:
		jstSourceLoader.loadClass(aClassname);	
		JaninoObjectCreator janino = new JaninoObjectCreator(jstSourceLoader.getClassMap());
		return janino.getObject(aClassname);
	}

// --- Implementation ---

	private JSTSourceLoader jstSourceLoader;
}

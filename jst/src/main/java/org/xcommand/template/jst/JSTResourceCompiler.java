package org.xcommand.template.jst;

import org.xcommand.technology.janino.JaninoObjectCreator;

public class JSTResourceCompiler
{

	public JSTJavaResourceLoader getJstJavaResourceLoader()
	{
		return jstJavaResourceLoader;
	}

	public void setJstJavaResourceLoader(JSTJavaResourceLoader jstJavaResourceLoader)
	{
		this.jstJavaResourceLoader = jstJavaResourceLoader;
	}

	public Object getObject(String aClassname)
	{
		// Compile parsed JST source, instatiate object and execute it:
		jstJavaResourceLoader.load(aClassname);
		JaninoObjectCreator janino = new JaninoObjectCreator(jstJavaResourceLoader.getClassMap());
		return janino.getObject(aClassname);
	}

	private JSTJavaResourceLoader jstJavaResourceLoader;
}

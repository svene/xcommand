package org.xcommand.template.jst;

import org.xcommand.technology.janino.JaninoObjectCreator;

public class JSTResourceCompiler {

	public JSTResourceCompiler(JSTJavaResourceLoader jstJavaResourceLoader) {
		this.jstJavaResourceLoader = jstJavaResourceLoader;
	}

	public JSTJavaResourceLoader getJstJavaResourceLoader() {
		return jstJavaResourceLoader;
	}

	public Object getObject(String aClassname) {
		// Compile parsed JST source, instatiate object and execute it:
		jstJavaResourceLoader.load(aClassname);
		var janino = new JaninoObjectCreator(jstJavaResourceLoader.getClassMap());
		return janino.getObject(aClassname);
	}

	private final JSTJavaResourceLoader jstJavaResourceLoader;
}

package org.xcommand.technology.janino;

import org.codehaus.commons.compiler.util.resource.MapResourceCreator;
import org.codehaus.commons.compiler.util.resource.MapResourceFinder;
import org.codehaus.commons.compiler.util.resource.ResourceCreator;
import org.codehaus.janino.CachingJavaSourceClassLoader;


import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

public class JaninoObjectCreator {

	public JaninoObjectCreator(Map<String, byte[]> aJavaSourceMap) {
		javaSourceResourceFinder = new XCMapResourceFinder(aJavaSourceMap);
		javaClassResourceFinder = new MapResourceFinder(javaClassMap);
	}

	public Class<?> getClass(String aClassname) {
		ClassLoader parentClassLoader = getClass().getClassLoader();
//		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, javaSourceResourceFinder, encoding, DebuggingInformation.ALL);
		//TODO: implement caching loader:
		ResourceCreator classFileCacheResourceCreator = new MapResourceCreator(javaClassMap);
		ClassLoader cl = new CachingJavaSourceClassLoader(
			parentClassLoader,
			javaSourceResourceFinder,
			StandardCharsets.UTF_8.name(),
			javaClassResourceFinder,
			classFileCacheResourceCreator
		);
		try {
			String dotClassName = aClassname.replace('/', '.');
			return cl.loadClass(dotClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	public Object getObject(String aClassname) {
		Class<?> clazz = getClass(aClassname);
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final Map<String, byte[]> javaClassMap = new HashMap<>() {
		@Override
		public byte[] get(Object key) {
			System.out.println("JaninoObjectCreator.get()");
			return super.get(key);
		}

		@Override
		public byte[] put(String key, byte[] value) {
			System.out.println("JaninoObjectCreator.put()");
			return super.put(key, value);
		}
	};
	private final XCMapResourceFinder javaSourceResourceFinder;
	private final MapResourceFinder javaClassResourceFinder;

}

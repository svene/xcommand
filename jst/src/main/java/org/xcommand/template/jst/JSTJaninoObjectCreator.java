package org.xcommand.template.jst;

import org.jooq.lambda.Sneaky;
import org.xcommand.technology.janino.XCMapResourceFinder;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.codehaus.janino.JavaSourceClassLoader;
import org.xcommand.util.FilesUnchecked;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.stream.Collectors;

public class JSTJaninoObjectCreator {

	public void initialize() {
		// Put source of template into `janinoClassMap' so that Janino can work with it:
		Map<String, byte[]> newJaninoClassMap = new HashMap<>();
		var classMap = jstScannerCV.getClassMap();
		Map<String, ClassMapEntry> newClassMap = new HashMap<>();
		classMap.forEach((key, cme) -> {
			newJaninoClassMap.put(key, cme.fme().content.getBytes());
			var newFme = cme.fme().toBuilder()
				.lastmodified(FilesUnchecked.getLastModifiedTime(cme.fme().path))
				.build();
			var newCme = cme.with().fme(newFme).build();
			newClassMap.put(key, newCme);
		});
		jstScannerCV.setClassMap(newClassMap);
		janinoClassMap = newJaninoClassMap;
		mrf = new XCMapResourceFinder(janinoClassMap);
	}

	public Class<?> getClass(String aClassname) {
		// Make sure classes are loaded:
		var classMap = jstScannerCV.getClassMap();
		var cme = classMap.get(aClassname);
		if (cme != null) {
			System.out.println("cme for '" + aClassname + "' found");
			if (cme.lastloaded() > cme.fme().lastmodified) {
				System.out.println("loaded class still valid.");
				return cme.clazz();
			}
		}
		// Load class via Janino:
		var parentClassLoader = getClass().getClassLoader();
		System.out.println("loading class '" + aClassname + "'");
		ClassLoader cl = new JavaSourceClassLoader(parentClassLoader, mrf, StandardCharsets.UTF_8.name());
		var dotClassName = aClassname.replace('/', '.');
		try {
			var clazz = cl.loadClass(dotClassName);
			var newCme = cme.with().clazz(clazz).lastloaded(new Date().getTime()).build();
			classMap.put(aClassname, newCme);
			return clazz;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	public Object newObject(String aClassname) {
		var clazz = getClass(aClassname);
		return Sneaky.supplier(() -> clazz.getDeclaredConstructor().newInstance()).get();
	}

	public void setJstProvider(IJSTProvider aJstProvider) {
		aJstProvider.getChangeNotifier().registerObserver(this::initialize);
	}

	private Map<String, byte[]> janinoClassMap = new HashMap<>();
	XCMapResourceFinder mrf;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTScannerCV jstScannerCV = dbp.newBeanForInterface(IJSTScannerCV.class);
}

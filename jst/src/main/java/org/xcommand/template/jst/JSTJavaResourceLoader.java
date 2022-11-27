package org.xcommand.template.jst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.jst.parser.JSTParser;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class JSTJavaResourceLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public Map<String, byte[]> getClassMap() {
		return classMap;
	}

	/**
	 * Load source of template for `aClassname' specified in full qualified
	 * java package notation (as 'java.lang.String')
	 */
	public void load(String resourceName) {
		LOGGER.info("resourceName: " + resourceName);

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
		if (is == null) {
			throw new RuntimeException("resource '%s' not found".formatted(resourceName));
		}

		try {
			jstParserCV.setInputStream(is);
			JSTParser parser = new DefaultJSTParserProvider().newJSTParser();
			jstParserCV.setGeneratedJavaCode(new StringBuffer());
			parser.Start();
			String s = jstParserCV.getGeneratedJavaCode().toString();

			classMap = new HashMap<>();
			classMap.put(resourceName.replace(".java", ""), s.getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private Map<String, byte[]> classMap = new HashMap<>();

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}

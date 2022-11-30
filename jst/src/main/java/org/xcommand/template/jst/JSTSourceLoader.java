package org.xcommand.template.jst;

import org.xcommand.template.jst.parser.JSTParser;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class JSTSourceLoader {

	public String getSrcDir() {
		return srcDir;
	}

	public Map<String, byte[]> getClassMap() {
		return classMap;
	}

	public void setSrcDir(String aSrcDir) {
		srcDir = aSrcDir;
	}

	/**
	 * Load source of template for `aClassname' specified in full qualified
	 * java package notation (as 'java.lang.String')
	 */
	public void loadJavaFile(String aClassname) {
		try {
//			TCP.pushContext(new HashMap());
			var filename = srcDir + "/" + aClassname.replaceAll("\\.", "/") + ".java";
			System.out.println("filename = " + filename);
			var is = new FileInputStream(filename);
//			String ss = new String(is.readAllBytes());
			jstParserCV.setInputStream(is);
			var parser = new DefaultJSTParserProvider().newJSTParser();
//		TCP.popContext();
			jstParserCV.setGeneratedJavaCode(new StringBuffer());
			parser.Start();
			var s = jstParserCV.getGeneratedJavaCode().toString();
//		System.out.println(s);

			classMap = new HashMap<>();
			System.out.println("aClassname = " + aClassname);
			classMap.put(aClassname, s.getBytes());
			System.out.println();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}


	private String srcDir;
	private Map<String, byte[]> classMap = new HashMap<>();

	private String getClassnameFromFilename(String aSrcDir, String aAbsolutePath) {
		var idx = aAbsolutePath.indexOf(aSrcDir);
		if (idx == -1) {
			throw new RuntimeException("cannot find source path '" + aSrcDir + "' in path of file '" + aAbsolutePath + "'");
		}

		var className = aAbsolutePath.substring(idx + aSrcDir.length() + 1, aAbsolutePath.lastIndexOf("."));
		System.out.println("className = " + className);
		return className;
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}

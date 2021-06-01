package org.xcommand.template.jst;

import org.xcommand.template.jst.parser.JSTParser;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class JSTSourceLoader
{

// --- Access ---

	public String getSrcDir()
	{
		return srcDir;
	}

	public Map getClassMap()
	{
		return classMap;
	}

// --- Setting ---

	public void setSrcDir(String aSrcDir)
	{
		srcDir = aSrcDir;
	}

// --- Processing ---

	/**
	 * Load source of template for `aClassname' specified in full qualified
	 * java package notation (as 'java.lang.String')
	 */
	public void loadClass(String aClassname)
	{
		try
		{
//			TCP.pushContext(new HashMap());
			String filename = srcDir + "/" + aClassname.replaceAll("\\.", "/") + ".java";
			System.out.println("filename = " + filename);
//		FileInputStream is = new FileInputStream("xc-webapp/jst/src/T1.java");
			FileInputStream is = new FileInputStream(filename);
			jstParserCV.setInputStream(is);
			JSTParser parser = new DefaultJSTParserProvider().newJSTParser();
//		TCP.popContext();
			jstParserCV.setGeneratedJavaCode(new StringBuffer());
			parser.Start();
			String s = jstParserCV.getGeneratedJavaCode().toString();
//		System.out.println(s);

			classMap = new HashMap();
			System.out.println("aClassname = " + aClassname);
			classMap.put(aClassname, s.getBytes());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}


// --- Implementation ---

	private String srcDir;
	private Map classMap = new HashMap();

	private String getClassnameFromFilename(String aSrcDir, String aAbsolutePath)
	{
		int idx = aAbsolutePath.indexOf(aSrcDir);
		if (idx == -1)
		{
			throw new RuntimeException("cannot find source path '" + aSrcDir + "' in path of file '" + aAbsolutePath + "'");
		}

		String className = aAbsolutePath.substring(idx + aSrcDir.length() + 1, aAbsolutePath.lastIndexOf("."));
		System.out.println("className = " + className);
		return className;
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}

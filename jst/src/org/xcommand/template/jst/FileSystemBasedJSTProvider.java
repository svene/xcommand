package org.xcommand.template.jst;

import org.xcommand.pattern.observer.SubjectImpl;
import org.xcommand.pattern.observer.ISubject;
import org.xcommand.template.jst.parser.JSTParser;
import org.apache.commons.io.FileUtils;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;

public class FileSystemBasedJSTProvider implements IJSTProvider
{

	public void initialize(Map aCtx)
	{
		// Just for a test:
		//new ServletContextBasedJSTProvider().initialize(aCtx);
		System.out.println("FileSystemBasedJSTProvider.initialize");
		classMap = new HashMap();
		Iterator it1 = srcDirs.iterator();
		// Loop over all configured source directories,
		// load and massage java source files and put the result into `classMap`:
		// TODO: factor this code out and add file-change-watcher
		while (it1.hasNext())
		{
			String srcDir = (String) it1.next();
			System.out.println("srcDir = " + srcDir);
			Iterator it = new JavaTemplateDirectoryIteratorProvider().newIterator(srcDir);
			while (it.hasNext())
			{
				ClassMapEntry cme = new ClassMapEntry();
				File file = (File) it.next();
				cme.file = file;
				cme.lastmodified = file.lastModified();
				String className = getClassnameFromFilename(srcDir, file.getAbsolutePath());
				classMap.put(className, cme);

				// Invoke TemplateToSourceGenerator:
				Map ctx = new HashMap();
				try
				{
					FileInputStream is = new FileInputStream(file);
					ctx.put("inputstream", is);
					ctx.put("classname", className);
					if (genSourceDir != null)
					{
						ctx.put("gensourcedir", genSourceDir);
					}
//					TemplateToSourceGenerator t2sg = new TemplateToSourceGenerator();
//					t2sg.execute(ctx);
//					cme.source = (String) ctx.get("source");

					Map cctx = new HashMap();
					JSTParserCV.setInputStream(cctx, is);
					JSTParser parser = new DefaultJSTParserProvider().newJSTParser(cctx);

					JSTParserCV.setGeneratedJavaCode(ctx, new StringBuffer());
					parser.Start(ctx);
					cme.source = JSTParserCV.getGeneratedJavaCode(ctx).toString();

					// Write source code as file to disk:
					if (genSourceDir != null)
					{
						String dirName = genSourceDir + "/";
						File dir = new File(genSourceDir);
						System.out.println("gensrcdir.path=" + dir.getAbsolutePath());
						File rf = new File(genSourceDir + "/" + className + ".java");
						rf.createNewFile();
						FileUtils.writeStringToFile(rf, cme.source);
					}

				}
				catch (Exception e)
				{
					throw new RuntimeException(e);
				}

			}
		}
	}

	public ClassMapEntry getClassMapEntry(Map aCtx, String aClassname)
	{
		ClassMapEntry cme = (ClassMapEntry) classMap.get(aClassname);
		if (cme != null)
		{
			// Check if file changed:
			if (cme.file.lastModified() > cme.lastmodified)
			{
				System.out.println("Template '" + cme.file.getAbsolutePath() + "' changed. Reloading templates (" +
					new Date() + ")");
				initialize(aCtx);
				changeNotifier.execute(new HashMap());
				cme = (ClassMapEntry) classMap.get(aClassname);
			}
			return cme;
		}
		// Not found so call `initialize' again because it might have been added in the
		// meanwhile (usually while developing) and then try to find it again:
		initialize(aCtx);
		changeNotifier.execute(new HashMap());

		cme = (ClassMapEntry) classMap.get(aClassname);
		if (cme != null)
		{
			return cme;
		}
		else
		{
			// File simply does not exist:
			throw new RuntimeException("template for class '" + aClassname + "' cannot be found.");
		}
	}

	public void setSrcDirs(List aSrcDirs)
	{
		srcDirs = aSrcDirs;
	}

	public Map getClassMap()
	{
		return classMap;
	}

	public void setGenSourceDir(String aGenSourceDir)
	{
		genSourceDir = aGenSourceDir;
	}

	public ISubject getChangeNotifier()
	{
		return changeNotifier;
	}

// --- Implementation ---

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

	private List srcDirs;
	private Map classMap = new HashMap();
	private ISubject changeNotifier = new SubjectImpl();
	private String genSourceDir;
}




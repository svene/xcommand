package org.collage.template;

import org.xcommand.core.IXCommand;

import java.util.Map;
import java.util.HashMap;

public class StaticStringTemplateCompiler
{
	public IXCommand getTemplateCommand(String aStringTemplate)
	{
		synchronized(templateCache)
		{
			IXCommand cmd = (IXCommand) templateCache.get(aStringTemplate);
			if (cmd == null)
			{
//				System.out.println("StaticStringTemplateCompiler.getTemplateCommand(): creating template for '" +
//					aStringTemplate + "'");
				// Create template and put it into `templateCache':
				cmd = textTemplateCompiler.newTemplateCommandFromString(aStringTemplate);
				templateCache.put(aStringTemplate, cmd);
			}
			else
			{
//				System.out.println("StaticStringTemplateCompiler.getTemplateCommand(): found template for '" +
//					aStringTemplate + "' in cache");
			}
			return cmd;
		}
	}

// --- Implementation ---
	private static final Map templateCache = new HashMap();
	private static TextTemplateCompiler textTemplateCompiler = new TextTemplateCompiler();

}

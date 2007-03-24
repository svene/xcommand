package org.collage.dom.evaluator.java.independent;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class JavaTemplateCmdCV
{

// --- Access ---

	public static IXCommand getTemplateComand(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_TEMPLATE_COMMAND);
	}

// --- Setting ---

	public static void setTemplateComand(Map aCtx, IXCommand aTemplateComand)
	{
		aCtx.put(KEY_TEMPLATE_COMMAND, aTemplateComand);
	}

// --- Implementation ---

	public static final String NS = "org.collage.dom.evaluator.java.independent.JavaTemplateCmdCV.";
	public static final String KEY_TEMPLATE_COMMAND = NS + "TEMPLATE_COMMAND";

}

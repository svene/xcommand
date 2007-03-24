package org.collage.dom.evaluator.java.independent;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class JavaTemplateCmdCV
{

// --- Access ---

	public static IXCommand getTemplateInstance(Map aCtx)
	{
		return (IXCommand) aCtx.get(KEY_TEMPLATE_INSTANCE);
	}

// --- Setting ---

	public static void setTemplateInstance(Map aCtx, IXCommand aTemplateInstance)
	{
		aCtx.put(KEY_TEMPLATE_INSTANCE, aTemplateInstance);
	}

// --- Implementation ---

	public static final String NS = "org.collage.dom.evaluator.java.independent.JavaTemplateCmdCV.";
	public static final String KEY_TEMPLATE_INSTANCE = NS + "TEMPLATE_INSTANCE";

}

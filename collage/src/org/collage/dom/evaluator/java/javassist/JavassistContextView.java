package org.collage.dom.evaluator.java.javassist;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class JavassistContextView
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

	public static final String NS = "org.collage.dom.evaluator.java.javassist.JavassistContextView.";
	public static final String KEY_TEMPLATE_INSTANCE = NS + "TEMPLATE_INSTANCE";

}
